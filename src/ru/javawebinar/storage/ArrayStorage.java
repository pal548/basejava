package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void saveInternal(Resume r) {
        storage[size] = r;
        size++;
    }

    protected void deleteInternal(int i) {
        // на место удаляемого переносим последний элемент
        size--;
        if (i < size) {
            storage[i] = storage[size];
        }
        storage[size] = null;
    }

    protected void updateInternal(int i, Resume r) {
        storage[i] = r;
    }

    protected int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
