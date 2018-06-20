package ru.javawebinar.basejava.exception;

import ru.javawebinar.basejava.sql.RuntimeSQLException;

public class AlreadyExistsException extends StorageException {
    public AlreadyExistsException(String uuid) {
        super(getTitle(uuid), uuid);
    }

    public AlreadyExistsException(String uuid, Exception e) {
        super(getTitle(uuid), uuid, e);
    }

    private static String getTitle(String uuid) {
        return "Resume " + uuid + " already exists in storage";
    }
}
