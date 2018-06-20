package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PrepareStatementRunner {
    public void Run(PreparedStatement ps) throws SQLException;
}
