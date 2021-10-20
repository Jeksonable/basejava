package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        int index = indexOf(r.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT) {
                int saveIndex = -(index + 1);
                System.arraycopy(storage, saveIndex, storage, saveIndex + 1, size - saveIndex);
                storage[saveIndex] = r;
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
            size--;
            System.arraycopy(storage, index + 1, storage, index, size - index);
        }
    }

    protected int indexOf(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
