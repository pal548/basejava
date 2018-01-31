import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;
    void clear() {
        for(int i = 0; i < size; i++){
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if(size < storage.length) {
            storage[size] = r;
            size++;
        }

    }

    Resume get(String uuid) {
        for(int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if(size == 0) return;
        for(int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                // на место удаляемого переносим последний элемент
                size--;
                if(i < size) {
                    storage[i] = storage[size];
                }
                storage[size] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage,size);
    }

    int size() {
        return size;
    }
}
