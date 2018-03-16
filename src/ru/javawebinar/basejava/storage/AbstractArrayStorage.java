package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.AlreadyExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    public static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        int i = find(uuid);
        if (i > -1) {
            return storage[i];
        } else {
            throw new NotFoundException(uuid);
        }
    }

    public void save(Resume r) {
        if (size >= storage.length) {
            throw new StorageException("Save error: storage is full", r.getUuid());
        }
        int i = find(r.getUuid());
        if (i > -1) {
            throw new AlreadyExistsException(r.getUuid());
        } else {
            saveInternal(r, i);
            size++;
        }

    }

    public void delete(String uuid) {
        int i = find(uuid);
        if (i > -1) {
            deleteInternal(i);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotFoundException(uuid);
        }
    }

    public void update(Resume r) {
        int i = find(r.getUuid());
        if (i < 0) {
            throw new NotFoundException(r.getUuid());
        } else {
            storage[i] = r;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int find(String uuid);

    protected abstract void saveInternal(Resume r, int i);

    protected abstract void deleteInternal(int i);
}
