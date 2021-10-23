package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = indexOf(r.getUuid());
        if (index < 0) {
            System.out.println("Update error: resume " + r + " is out of storage!");
        } else {
            storage[index] = r;
            System.out.println("Resume " + r + " is updated!");
        }
    }

    public void save(Resume r) {
        int index = indexOf(r.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT) {
                insertResume(r, index);
                size++;
            } else {
                System.out.println("Save error: storage is full!");
            }
        } else {
            System.out.println("Save error: resume " + r + " is already in storage!");
        }
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            System.out.println("Get error: resume " + uuid + " is out of storage!");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            System.out.println("Delete error: resume " + uuid + " is out of storage!");
        } else {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract void insertResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    protected abstract int indexOf(String uuid);
}
