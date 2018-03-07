package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    protected void saveInternal(Resume r) {
        int low = 0;
        int high = size - 1;

        while (low < high) {
            int mid = (low + high) >>> 1;

            int cmp = storage[mid].compareTo(r);

            if (cmp <= 0)
                low = mid + 1;
            else
                high = mid - 1;
        }
        // low = high
        System.arraycopy(storage, low, storage, low + 1, size - low);
        if (size == 0 || r.compareTo(storage[low]) < 0)
            storage[low] = r;
        else
            storage[low + 1] = r;
        size++;
    }

    protected void deleteInternal(int i) {
        System.arraycopy(storage, i+1, storage, i, size - i - 1);
        storage[size - 1] = null;
        size--;
    }

    protected int find(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }
}
