package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (size < storage.length) {
            if (find(r.getUuid()) > -1) {
                System.out.println("Save error: resume is already in storage");
                return;
            }
            storage[size] = r;
            size++;
        } else {
            System.out.println("Save error: storage is full");
        }

    }

    public void delete(String uuid) {
        int i = find(uuid);
        if (i > -1) {
            // на место удаляемого переносим последний элемент
            size--;
            if (i < size) {
                storage[i] = storage[size];
            }
            storage[size] = null;
        } else {
            System.out.println("Delete error: resume with uuid \"" + uuid + "\" not found");
        }
    }

    public void update(String uuid, Resume r) {
        int i = find(uuid);
        if (i > -1) {
            storage[i] = r;
        } else {
            System.out.println("Update error: resume with uuid \"" + uuid + "\" not found");
        }
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
