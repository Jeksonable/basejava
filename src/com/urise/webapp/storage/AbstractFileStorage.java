package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writeable");
        }
        this.directory = directory;
    }

    @Override
    protected File searchKeyOf(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateResume(File file, Resume r) {
        try {
            writeResume(r, file);
        } catch (IOException e) {
            throw new StorageException("Update resume is failed", r.getUuid(), e);
        }
    }

    @Override
    protected void saveResume(Resume r, File file) {
        try {
            file.createNewFile();
            writeResume(r, file);
        } catch (IOException e) {
            throw new StorageException("Save resume is failed", r.getUuid(), e);
        }
    }

    protected abstract void writeResume(Resume r, File file) throws IOException;

    @Override
    protected Resume getResume(File file) {
        Resume r;
        try {
            r = readResume(file);
        } catch (IOException e) {
            throw new StorageException("Get resume is failed", file.getName(), e);
        }
        return r;
    }

    protected abstract Resume readResume(File file) throws IOException;

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> resumeList = new ArrayList<>();
        try {
            String[] fileNames = directory.list();
            if (fileNames != null) {
                for (String fileName : fileNames) {
                    File file = new File(directory.getCanonicalPath() + "/" + fileName);
                    resumeList.add(readResume(file));
                }
            }
        } catch (Exception e) {
            throw new StorageException("Get all resumes is failed", "", e);
        }
        return resumeList;
    }

    @Override
    public void clear() {
        try {
            String[] fileNames = directory.list();
            if (fileNames != null) {
                for (String fileName : fileNames) {
                    File file = new File(directory.getCanonicalPath() + "/" + fileName);
                    file.delete();
                }
            }
        } catch (Exception e) {
            throw new StorageException("Clear failed", "", e);
        }
    }

    @Override
    public int size() {
        return directory.list() == null ? 0 : directory.list().length;
    }
}
