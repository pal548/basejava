package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
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
            System.out.println("Get resume error: resume with uuid \"" + uuid + "\" not found");
            return null;
        }
    }

    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("Save error: storage is full");
            return;
        }
        int i = find(r.getUuid());
        if (i > -1) {
            System.out.println("Save error: resume is already in storage");
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
            System.out.println("Delete error: resume with uuid \"" + uuid + "\" not found");
        }
    }

    public void update(Resume r) {
        int i = find(r.getUuid());
        if (i < 0) {
            System.out.println("Update error: resume with uuid \"" + r.getUuid() + "\" not found");
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
