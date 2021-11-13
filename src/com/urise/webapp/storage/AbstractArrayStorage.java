package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

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
    protected void updateResume(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    public void save(Resume r) {
        super.save(r);
        size++;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void checkOverflow(Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Save error: storage is full!", r.getUuid());
        }
    }
}
