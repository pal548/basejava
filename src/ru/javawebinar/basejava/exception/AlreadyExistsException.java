package ru.javawebinar.basejava.exception;

public class AlreadyExistsException extends StorageException {
    public AlreadyExistsException(String uuid) {
        super("Resume " + uuid + " already exists in storage", uuid);
    }
}
