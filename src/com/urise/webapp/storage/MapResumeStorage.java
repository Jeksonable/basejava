package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected Object searchKeyOf(String uuid) {
        return storage.getOrDefault(uuid, null);
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
        Resume r = (Resume) searchKey;
        return storage.get(r.getUuid());
    }

    @Override
    protected void deleteResume(Object searchKey) {
        Resume r = (Resume) searchKey;
        storage.remove(r.getUuid());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedStorage = new ArrayList<>(storage.values());
        sortedStorage.sort(RESUME_NAME_COMPARATOR);
        return sortedStorage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist (Object searchKey) {
        return searchKey != null;
    }
}
