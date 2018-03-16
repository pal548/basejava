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
        super.delete();
        assertTrue(sorted());
    }

    private boolean sorted() {
        Resume[] arr = storage.getAll();
        if (arr.length <= 1) {
            return true;
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1].compareTo(arr[i]) > 0) {
                return false;
            }
        }
        return true;
    }
}