/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for(int i = 0; i < storage.length; i++){
            storage[i] = null;
        }
    }

    void save(Resume r) {
        boolean found = false;
        int i = 0;
        //int foundIndex = -1;
        while (!found && (i < storage.length)) {
            if (storage[i] == null) {
                found = true;
                //foundIndex = i;
                storage[i] = r;
                break;
            }
            i++;
        }
    }

    Resume get(String uuid) {
        for(int i = 0; i < storage.length; i++) {
            if ((storage[i] != null) && (storage[i].uuid == uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for(int i = 0; i < storage.length; i++) {
            if ((storage[i] != null) && (storage[i].uuid == uuid)) {
                storage[i] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = new Resume[size()];
        int j = 0;
        for(int i = 0; i < storage.length; i++){
            if(storage[i] != null){
                result[j] = storage[i];
                j++;
            }
        }
        return result;
    }

    int size() {
        int size = 0;
        for(int i = 0; i < storage.length; i++){
            if(storage[i] != null){
                size++;
            }
        }
        return size;
    }
}
