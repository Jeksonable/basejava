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
            throw new StorageException(e);
        }
    }

    public interface Handler<T> {
        T handle(PreparedStatement ps) throws SQLException;
    }

    public void checkSqlException(SQLException e, String uuid) throws StorageException {
        String existMessage = "ОШИБКА: повторяющееся значение ключа нарушает ограничение уникальности \"resume_pkey\"\n";
        if (e.getMessage().startsWith(existMessage)) {
            throw new ExistStorageException(uuid);
        }
        throw new StorageException(e);
    }
}
