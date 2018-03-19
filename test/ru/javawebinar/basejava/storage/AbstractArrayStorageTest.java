package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

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
        storage.save(new Resume());
    }

    protected void fillRandom() {
        storage.clear();
        for(int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
    }
}