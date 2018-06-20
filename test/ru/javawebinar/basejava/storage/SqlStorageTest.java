package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(),Config.get().getDbUser(),Config.get().getDbPassword()));
    }

    protected void finalize() {
        ((SqlStorage)storage).closeConnection();
    }
}
