package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void checkSaveSorting() {
        fillRandom();
        assertTrue(sorted());
    }

    @Test
    @Override
    public void delete() throws Exception {
        storage.delete(r1.getUuid());
        assertTrue(sorted());
    }

}