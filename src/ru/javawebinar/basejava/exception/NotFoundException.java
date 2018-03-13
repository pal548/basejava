package ru.javawebinar.basejava.exception;

public class NotFoundException extends StorageException {
    public NotFoundException(String uuid) {
        super("Resume " + uuid + " is not found in storage", uuid);
    }
}
