package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected int indexOf(String uuid) {
        if (storage.containsKey(uuid)) {
            return 0;
        }
        return -1;
    }

    @Override
    protected void updateResume(int index, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
