package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume searchKeyOf(String uuid) {
        return storage.getOrDefault(uuid, null);
    }

    @Override
    protected void updateResume(Resume searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Resume r, Resume searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Resume searchKey) {
        return storage.get(searchKey.getUuid());
    }

    @Override
    protected void deleteResume(Resume searchKey) {
        storage.remove(searchKey.getUuid());
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
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(storage.values());
    }
}
