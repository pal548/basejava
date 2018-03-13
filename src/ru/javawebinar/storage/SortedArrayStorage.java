package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    protected void saveInternal(Resume r, int i) {
        i = -i - 1; // преобразуем результат Arrays.binarySearch в индекс
        System.arraycopy(storage, i, storage, i + 1, size - i);
        storage[i] = r;
    }

    protected void deleteInternal(int i) {
        System.arraycopy(storage, i + 1, storage, i, size - i - 1);
    }

    protected int find(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }
}
