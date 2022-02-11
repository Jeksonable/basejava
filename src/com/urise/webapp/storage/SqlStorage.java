package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private static final Comparator<Resume> RESUME_NAME_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.dbConnect("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.dbConnect("UPDATE resume SET full_name =? WHERE uuid =?", ps -> {
            LOG.info("Update " + r);
            String uuid = r.getUuid();
            ps.setString(1, r.getFullName());
            ps.setString(2, uuid);
            checkExist(ps.executeUpdate() == 0, uuid);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.dbConnect("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            LOG.info("Save " + r);
            String uuid = r.getUuid();
            ps.setString(1, uuid);
            ps.setString(2, r.getFullName());
            try {
                ps.execute();
            } catch (SQLException e) {
                sqlHelper.checkSqlException(e, uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.dbConnect("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            LOG.info("Get " + uuid);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            checkExist(!rs.next(), uuid);
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.dbConnect("DELETE FROM resume WHERE uuid =?", ps -> {
            LOG.info("Delete " + uuid);
            ps.setString(1, uuid);
            checkExist(ps.executeUpdate() == 0, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.dbConnect("SELECT * FROM resume", ps -> {
            LOG.info("GetAllSorted");
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            resumes.sort(RESUME_NAME_COMPARATOR);
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.dbConnect("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void checkExist(boolean check, String uuid) {
        if (check) {
            throw new NotExistStorageException(uuid);
        }
    }
}
