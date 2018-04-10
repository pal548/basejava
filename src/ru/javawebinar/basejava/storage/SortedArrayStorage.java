package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveInternalInternal(Resume r, int i) {
        i = -i - 1; // преобразуем результат Arrays.binarySearch в индекс
        System.arraycopy(storage, i, storage, i + 1, size - i);
        storage[i] = r;
    }

    @Override
    protected void deleteInternalInternal(int i) {
        System.arraycopy(storage, i + 1, storage, i, size - i - 1);
    }

    @Override
    protected Integer find(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid, "", null));
    }
}
