package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    protected static final Comparator<Resume> RESUME_NAME_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        updateResume(getSearchKeyExistResume(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = searchKeyOf(r.getUuid());
        if (isExist(searchKey)) {
            LOG.warning("Save error: resume " + r + " is already in storage!");
            throw new ExistStorageException(r.getUuid());
        }
        saveResume(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return getResume(getSearchKeyExistResume(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        deleteResume(getSearchKeyExistResume(uuid));
    }

    private SK getSearchKeyExistResume(String uuid) {
        SK searchKey = searchKeyOf(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Update error: resume " + uuid + " is out of storage!");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> allResumes = new ArrayList<>(getResumeList());
        allResumes.sort(RESUME_NAME_COMPARATOR);
        return allResumes;
    }

    protected abstract SK searchKeyOf(String uuid);

    protected abstract void updateResume(SK searchKey, Resume r);

    protected abstract void saveResume(Resume r, SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract void deleteResume(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> getResumeList();
}
