package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        updateResume(getSearchKeyExistResume(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        Object searchKey = searchKeyOf(r.getUuid());
        if (searchKey instanceof String || (Integer) searchKey >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        saveResume(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        return getResume(getSearchKeyExistResume(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteResume(getSearchKeyExistResume(uuid));
    }

    private Object getSearchKeyExistResume(String uuid) {
        Object searchKey = searchKeyOf(uuid);
        if (searchKey instanceof Integer && (Integer) searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object searchKeyOf(String uuid);

    protected abstract void updateResume(Object searchKey, Resume r);

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void deleteResume(Object searchKey);
}
