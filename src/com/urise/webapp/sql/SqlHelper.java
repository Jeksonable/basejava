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

    public <T> T transactionalConnect(TransactionHandler<T> handler) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = handler.handle(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw checkSqlException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface Handler<T> {
        T handle(PreparedStatement ps) throws SQLException;
    }

    public interface TransactionHandler<T> {
        T handle(Connection conn) throws SQLException;
    }

    private StorageException checkSqlException(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(e);
        }
        return new StorageException(e);
    }
}
