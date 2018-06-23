package ru.javawebinar.basejava.sql;

import javax.management.RuntimeMBeanException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public <T> T execSQL(String sql, PrepareStatementRunner<T> psr) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            return psr.Run(ps);
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public int execSQL(String sql) {
        return execSQL(sql, PreparedStatement::executeUpdate);
    }
}
