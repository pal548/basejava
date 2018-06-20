package ru.javawebinar.basejava.sql;

import java.sql.SQLException;

public class RuntimeSQLException extends RuntimeException {
    public RuntimeSQLException(SQLException e) {
        super(e);
    }
}
