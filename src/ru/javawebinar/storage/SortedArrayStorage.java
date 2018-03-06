package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    protected void saveInternal(Resume r) {
        storage[size] = r;
        size++;
        sortStorage();
    }

    protected void deleteInternal(int i) {
        for (int j = i; j < size - 1; j++) {
            storage[j] = storage[j + 1];
        }
        storage[size - 1] = null;
        size--;
    }

    protected void updateInternal(int i, Resume r) {
        storage[i] = r;
        sortStorage();
    }


    protected int find(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    private void sortStorage() {
        Arrays.sort(storage, 0, size);
    }
}
