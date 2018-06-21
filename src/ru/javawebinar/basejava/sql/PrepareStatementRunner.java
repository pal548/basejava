package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PrepareStatementRunner<T> {
    public T Run(PreparedStatement ps) throws SQLException;
}
