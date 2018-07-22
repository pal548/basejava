package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

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

    public <T> T transactionalExec(TransactionRunner<T> tr) {
        try(Connection con = getConnection()){
            try {
                con.setAutoCommit(false);
                T res = tr.run(con);
                con.commit();
                return res;
            } catch (SQLException e) {
                con.rollback();
                throw new RuntimeSQLException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user",dbUser);
        props.setProperty("password",dbPassword);
        props.setProperty("ssl","true");
        props.setProperty("sslfactory","org.postgresql.ssl.NonValidatingFactory");
        return DriverManager.getConnection(dbUrl, props);
    }

    public int execSQL(String sql) {
        return execSQL(sql, PreparedStatement::executeUpdate);
    }
}
