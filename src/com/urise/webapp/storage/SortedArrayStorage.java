package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_UUID_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void insertResume(Resume r, int index) {
        int saveIndex = -(index + 1);
        System.arraycopy(storage, saveIndex, storage, saveIndex + 1, size - saveIndex);
        storage[saveIndex] = r;
    }

    @Override
    protected void removeResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }

    @Override
    protected Object searchKeyOf(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_UUID_COMPARATOR);
    }
}
