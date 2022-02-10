package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;
import org.postgresql.util.PSQLException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.dbConnect(connectionFactory, "DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        SqlHelper.dbConnect(connectionFactory, "UPDATE resume SET full_name =? WHERE uuid =?", ps -> {
            LOG.info("Update " + r);
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.execute();
            checkExist(ps.getUpdateCount() == 0, r.getUuid());
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        SqlHelper.dbConnect(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            LOG.info("Save " + r);
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            try {
                ps.execute();
            } catch (PSQLException e) {
                throw new ExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.dbConnect(connectionFactory, "SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            LOG.info("Get " + uuid);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            checkExist(!rs.next(), uuid);
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.dbConnect(connectionFactory, "DELETE FROM resume WHERE uuid =?", ps -> {
            LOG.info("Delete " + uuid);
            ps.setString(1, uuid);
            ps.execute();
            checkExist(ps.getUpdateCount() == 0, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return SqlHelper.dbConnect(connectionFactory, "SELECT * FROM resume", ps -> {
            LOG.info("GetAllSorted");
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return SqlHelper.dbConnect(connectionFactory, "SELECT count(*) FROM resume", ps -> {
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
