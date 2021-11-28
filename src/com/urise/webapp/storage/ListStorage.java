package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateResume(Object searchKey, Resume r) {
        storage.add((int) searchKey, r);
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object searchKeyOf(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected List<Resume> getResumeList() {
        return storage;
    }
}
