package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            fillRandom();
        } catch (StorageException e) {
            fail();
        }
        storage.save(new Resume("John Grisham", null));
    }

    protected void fillRandom() {
        storage.clear();
        for(int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume(UUID.randomUUID().toString(), String.valueOf(ThreadLocalRandom.current().nextInt(9999))));
        }
    }
}