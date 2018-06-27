package ru.javawebinar.basejava.exception;

public class ExistsException extends StorageException {
    public ExistsException(String uuid) {
        super(getTitle(uuid), uuid);
    }

    public ExistsException(String uuid, Exception e) {
        super(getTitle(uuid), uuid, e);
    }

    private static String getTitle(String uuid) {
        return "Resume " + uuid + " already exists in storage";
    }
}
