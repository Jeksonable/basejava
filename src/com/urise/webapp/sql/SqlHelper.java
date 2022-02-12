package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T dbConnect(String command, Handler<T> handler) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(command)) {
            return handler.handle(ps);
        } catch (SQLException e) {
            throw checkSqlException(e);
        }
    }

    public interface Handler<T> {
        T handle(PreparedStatement ps) throws SQLException;
    }

    private StorageException checkSqlException(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(e);
        }
        return new StorageException(e);
    }
}
