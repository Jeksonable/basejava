package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        if (indexOf(r.getUuid()) < 0) {
            System.out.println("Update error: resume " + r + " is out of storage!");
        } else {
            System.out.println("Resume " + r + " is updated!");
        }
    }

    public void save(Resume r) {
        if (indexOf(r.getUuid()) < 0) {
            if (size < storage.length) {
                storage[size] = r;
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
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            System.out.println("Delete error: resume " + uuid + " is out of storage!");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
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

    private int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
