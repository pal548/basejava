package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.AlreadyExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    protected static final Resume r1 = new Resume(UUID_1);
    protected static final Resume r2 = new Resume(UUID_2);
    protected static final Resume r3 = new Resume(UUID_3);

    public AbstractStorageTest(Storage storage) {
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
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(storage.size(), list.size());
        assertTrue(list.contains(r1));
        assertTrue(list.contains(r2));
        assertTrue(list.contains(r3));
        assertTrue(sorted());
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    protected boolean sorted() {
        List<Resume> list = storage.getAllSorted();
        if (list.size() <= 1) {
            return true;
        }
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }
}
