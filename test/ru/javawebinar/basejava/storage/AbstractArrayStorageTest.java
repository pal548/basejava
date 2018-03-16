package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.AlreadyExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final Resume r1 = new Resume(UUID_1);
    private static final Resume r2 = new Resume(UUID_2);
    private static final Resume r3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void testGetSave() throws Exception {
        Resume r = new Resume();
        storage.save(r);
        assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = AlreadyExistsException.class)
    public void saveAlreadyExists() {
        Resume r = new Resume(UUID_1);
        storage.save(r);
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

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        storage.delete(r1.getUuid());
        storage.get(r1.getUuid());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotExisted() {
        Resume r = new Resume();
        storage.delete(r.getUuid());
    }

    @Test
    public void update() throws Exception {
        Resume r = new Resume(UUID_1);
        storage.update(r);
        assertTrue(storage.get(r.getUuid()) == r);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] arr = storage.getAll();
        assertEquals(storage.size(), arr.length);
        assertEquals(arr[0], r1);
        assertEquals(arr[1], r2);
        assertEquals(arr[2], r3);
    }

    @Test
    public void size() throws Exception {
        assertEquals(storage.size(),  3);
    }

    protected void fillRandom() {
        storage.clear();
        for(int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
    }
}