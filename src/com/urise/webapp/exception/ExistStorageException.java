package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(Exception e) {
        super(e);
    }

    public ExistStorageException(String uuid) {
        super("Save error: resume " + uuid + " is already in storage!", uuid);
    }
}
