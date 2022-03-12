package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.dbConnect("DELETE FROM resume",
                ps -> {
                    ps.execute();
                    return null;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalConnect(conn -> {
            LOG.info("Update " + r);
            String uuid = r.getUuid();
            executeCommand(conn, "" +
                            "UPDATE resume " +
                            "   SET full_name =? " +
                            " WHERE uuid =?",
                    ps -> {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, uuid);
                        checkExist(ps.executeUpdate() == 0, uuid);
                    });
            deleteContent(conn, uuid);
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalConnect(conn -> {
            LOG.info("Save " + r);
            executeCommand(conn, "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                    ps -> {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    });
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.dbConnect("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "LEFT JOIN section s " +
                        "       ON r.uuid = s.resume_uuid " +
                        "    WHERE r.uuid =? ",
                ps -> {
                    LOG.info("Get " + uuid);
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    checkExist(!rs.next(), uuid);
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContent(r, rs, true);
                        addContent(r, rs, false);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.dbConnect("DELETE FROM resume WHERE uuid =?",
                ps -> {
                    LOG.info("Delete " + uuid);
                    ps.setString(1, uuid);
                    checkExist(ps.executeUpdate() == 0, uuid);
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalConnect(conn -> {
            LOG.info("GetAllSorted");
            Map<String, Resume> resumes = new LinkedHashMap<>();
            cycleOperation(conn, "SELECT * FROM resume ORDER BY full_name, uuid",
                    rs -> {
                        String uuid = rs.getString("uuid");
                        resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                    });
            addAllContent(conn, resumes, true);
            addAllContent(conn, resumes, false);
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.dbConnect("SELECT count(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                });
    }

    private void checkExist(boolean check, String uuid) {
        if (check) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void executeCommand(Connection conn, String sql, StatementExecutor executor) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            executor.execute(ps);
        }
    }

    private void cycleOperation(Connection conn, String sql, ResultSetExecutor executor) throws SQLException {
        executeCommand(conn, sql, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                executor.execute(rs);
            }
        });
    }

    private void deleteContent(Connection conn, String uuid) throws SQLException {
        for (String contentType : new String[]{"contact", "section"}) {
            executeCommand(conn, "DELETE FROM " + contentType + " WHERE resume_uuid =?",
                    ps -> {
                        ps.setString(1, uuid);
                        ps.execute();
                    });
        }
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        executeCommand(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)",
                ps -> {
                    for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                        setValues(ps, r, e.getKey().name(), e.getValue());
                    }
                    ps.executeBatch();
                });
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        executeCommand(conn, "INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?,?,?)",
                ps -> {
                    for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                        SectionType type = e.getKey();
                        String description = "";
                        AbstractSection section = e.getValue();
                        switch (type) {
                            case PERSONAL:
                            case OBJECTIVE:
                                description = ((SimpleSection) section).getDescription();
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                description = ((BulletedListSection) section).getDescriptions()
                                        .stream()
                                        .reduce(description, (str, des) -> str + des + "\n");
                                break;
                            case EDUCATION:
                            case EXPERIENCE:
                        }
                        setValues(ps, r, type.name(), description);
                    }
                    ps.executeBatch();
                });
    }

    private void setValues(PreparedStatement ps, Resume r, String type, String description) throws SQLException {
        ps.setString(1, r.getUuid());
        ps.setString(2, type);
        ps.setString(3, description);
        ps.addBatch();
    }

    private void addContent(Resume r, ResultSet rs, boolean isContact) throws SQLException {
        String value = rs.getString(isContact ? "value" : "section_value");
        if (value != null && !value.isEmpty()) {
            if (isContact) {
                r.addContact(ContactType.valueOf(rs.getString("type")), value);
            } else {
                SectionType type = SectionType.valueOf(rs.getString("section_type"));
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(type, new SimpleSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new BulletedListSection(value.split("\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                }
            }
        }
    }

    private void addAllContent(Connection conn, Map<String, Resume> resumes, boolean isContact) throws SQLException {
        String contentType = isContact ? "contact" : "section";
        cycleOperation(conn, "SELECT * FROM " + contentType,
                rs -> addContent(resumes.get(rs.getString("resume_uuid")), rs, isContact));
    }

    private interface StatementExecutor {
        void execute(PreparedStatement ps) throws SQLException;
    }

    private interface ResultSetExecutor {
        void execute(ResultSet rs) throws SQLException;
    }
}
