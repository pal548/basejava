package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    Resume r1 = new Resume(UUID_1);
    Resume r2 = new Resume(UUID_2);
    Resume r3 = new Resume(UUID_3);

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
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void update() throws Exception {
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
    }

    @Test
    public void find() throws Exception {
    }

}