package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (indexOf(r.getUuid()) < 0) {
            if (size < STORAGE_LIMIT) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Save error: storage is full!");
            }
        } else {
            System.out.println("Save error: resume " + r + " is already in storage!");
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

    protected int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
