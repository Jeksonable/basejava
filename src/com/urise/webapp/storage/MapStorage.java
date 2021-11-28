package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected Object searchKeyOf(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected void updateResume(Object searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        String uuid = (String) searchKey;
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        String uuid = (String) searchKey;
        storage.remove(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Collection<Resume> getResumeList() {
        return storage.values();
    }
}
