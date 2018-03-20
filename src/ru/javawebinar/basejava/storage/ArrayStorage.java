package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveInternalInternal(Resume r, int i) {
        storage[size] = r;
    }

    @Override
    protected void deleteInternalInternal(int i) {
        // на место удаляемого переносим последний элемент
        storage[i] = storage[size - 1];
    }

    @Override
    protected Integer find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
