package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_NAME_COMPARATOR = Comparator.comparing(Resume::getFullName);

    @Override
    public void update(Resume r) {
        updateResume(getSearchKeyExistResume(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        Object searchKey = searchKeyOf(r.getUuid());
        if (isExist(searchKey)) {
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
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object searchKeyOf(String uuid);

    protected abstract void updateResume(Object searchKey, Resume r);

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void deleteResume(Object searchKey);

    protected abstract boolean isExist (Object searchKey);
}
