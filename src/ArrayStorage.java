import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (size < storage.length) {
            int i = find(r.uuid);
            if (i > -1) {
                System.out.printf("Save error: resume is already in storage");
                return;
            }
            storage[size] = r;
            size++;
        } else {
            System.out.println("Save error: storage is full");
        }

    }

    Resume get(String uuid) {
        int i = find(uuid);
        if (i > -1) {
            return storage[i];
        } else {
            System.out.println("Get resume error: resume with uuid \"" + uuid + "\" not found");
            return null;
        }
    }

    void delete(String uuid) {
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

    void update(String uuid, Resume r) {
        int i = find(uuid);
        if (i > -1) {
            storage[i] = r;
        } else {
            System.out.println("Update error: resume with uuid \"" + uuid + "\" not found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }

    private int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                return i;
            }
        }
        return -1;
    }
}
