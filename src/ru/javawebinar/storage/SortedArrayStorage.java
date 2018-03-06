package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        //TODO
    }

    public void delete(String uuid) {
        //TODO
    }

    public void update(String uuid, Resume r) {
        //TODO
    }

    protected int find(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }
}
