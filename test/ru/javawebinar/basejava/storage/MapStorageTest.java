package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void getAll() throws Exception {
        Resume[] arr = storage.getAll();
        assertEquals(storage.size(), arr.length);
        assertTrue(r1 == storage.get(r1.getUuid()));
        assertTrue(r2 == storage.get(r2.getUuid()));
        assertTrue(r3 == storage.get(r3.getUuid()));
    }
}