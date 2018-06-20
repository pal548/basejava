package ru.javawebinar.basejava.exception;

import ru.javawebinar.basejava.sql.RuntimeSQLException;

public class NotFoundException extends StorageException {
    public NotFoundException(String uuid) {
        super(getTitle(uuid), uuid);
    }

    public NotFoundException(String uuid, Exception e) {
        super(getTitle(uuid), uuid, e);
    }

    private static String getTitle(String uuid) {
        return "Resume " + uuid + " is not found in storage";
    }
}
