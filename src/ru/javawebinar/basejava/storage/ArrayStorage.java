package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void saveInternal(Resume r, int i) {
        storage[size] = r;
    }

    protected void deleteInternal(int i) {
        // на место удаляемого переносим последний элемент
        storage[i] = storage[size - 1];
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
