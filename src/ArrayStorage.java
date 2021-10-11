import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int i = 0;
        while (i < size()) {
            if (storage[i].uuid.equals(uuid)) {
                break;
            }
            i++;
        }
        while (i < size() - 1) {
            Resume temp = storage[i];
            storage[i] = storage[i + 1];
            storage[i + 1] = temp;
            i++;
        }
        storage[i] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int size = 0;
        while (storage[size] != null) {
            size++;
        }
        return size;
    }
}
