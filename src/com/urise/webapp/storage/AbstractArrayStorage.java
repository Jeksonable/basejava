package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateResume(Object searchKey, Resume r) {
        storage[(int) searchKey] = r;
    }

    @Override
    public void saveResume(Resume r, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Save error: storage is full!", r.getUuid());
        }
        insertResume(r, (int) searchKey);
        size++;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    public void deleteResume(Object searchKey) {
        removeResume((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedStorage = new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, size)));
        sortedStorage.sort(RESUME_NAME_COMPARATOR);
        return sortedStorage;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist (Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract void insertResume(Resume r, int index);

    protected abstract void removeResume(int index);
}
