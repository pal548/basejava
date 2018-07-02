package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionRunner<T> {
    T run(Connection con) throws SQLException;
}
