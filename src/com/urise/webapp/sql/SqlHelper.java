package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public static <T> T dbConnect(ConnectionFactory connectionFactory, String command, Handler<T> handler) {
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
}
