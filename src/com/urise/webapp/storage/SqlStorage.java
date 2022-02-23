package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
            executeCommand(conn, "DELETE FROM contact WHERE resume_uuid =?",
                    ps -> {
                        ps.setString(1, uuid);
                        ps.execute();
                    });
            insertContacts(conn, r);
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
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.dbConnect("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE r.uuid =? ",
                ps -> {
                    LOG.info("Get " + uuid);
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    checkExist(!rs.next(), uuid);
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(r, rs);
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
            cycleOperation(conn, "SELECT * FROM contact",
                    rs -> addContact(resumes.get(rs.getString("resume_uuid")), rs));
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

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        executeCommand(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)",
                ps -> {
                    for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, e.getKey().name());
                        ps.setString(3, e.getValue());
                        ps.addBatch();
                    }
                    ps.executeBatch();
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

    private void addContact(Resume r, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null && !value.isEmpty()) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }

    private interface StatementExecutor {
        void execute(PreparedStatement ps) throws SQLException;
    }

    private interface ResultSetExecutor {
        void execute(ResultSet rs) throws SQLException;
    }
}
