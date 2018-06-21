package ru.javawebinar.basejava.sql;

import javax.management.RuntimeMBeanException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final Connection connection;
    private boolean connectionOpened = false;
    private boolean connectionClosed = false;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            connectionOpened = true;
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }

    public <T> T execSQL(String sql, PrepareStatementRunner<T> psr) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            return psr.Run(ps);
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }

    public int execSQL(String sql) {
        return execSQL(sql, PreparedStatement::executeUpdate);
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        } finally {
            connectionClosed = true;
        }
    }

    protected void finalize() {
        if (connectionOpened && !connectionClosed) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }

    }
}
