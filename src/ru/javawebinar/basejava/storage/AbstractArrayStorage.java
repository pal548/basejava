package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    public static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean checkIndex(Integer index) {
        return index > -1;
    }

    @Override
    protected Resume getByIndex(Integer index) {
        return storage[index];
    }

    @Override
    public void save(Resume r) {
        if (size >= storage.length) {
            throw new StorageException("Save error: storage is full", r.getUuid());
        }
        super.save(r);
    }

    @Override
    protected void saveInternal(Resume r, Integer i) {
        saveInternalInternal(r, i);
        size++;
    }

    @Override
    public void deleteInternal(Integer i) {
        deleteInternalInternal(i);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void updateInternal(Integer i, Resume r) {
        storage[i] = r;
    }

    @Override
    protected List<Resume> getAllList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    public int size() {
        return size;
    }

    protected abstract void saveInternalInternal(Resume r, int i);

    protected abstract void deleteInternalInternal(int i);
}
