package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateResume(Integer searchKey, Resume r) {
        storage.add(searchKey, r);
    }

    @Override
    protected void saveResume(Resume r, Integer searchKey) {
        storage.add(r);
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer searchKeyOf(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected List<Resume> getResumeList() {
        return storage;
    }
}
