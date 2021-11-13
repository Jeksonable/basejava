package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        updateResume(getIndexExistResume(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        int index = indexOf(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        checkOverflow(r);
        insertResume(r, index);
    }

    @Override
    public Resume get(String uuid) {
        return getResume(getIndexExistResume(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteResume(getIndexExistResume(uuid));
    }

    private int getIndexExistResume(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract int indexOf(String uuid);

    protected abstract void updateResume(int index, Resume r);

    protected abstract void checkOverflow(Resume r);

    protected abstract void insertResume(Resume r, int index);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(int index);
}
