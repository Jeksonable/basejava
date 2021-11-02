package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Update error: resume " + uuid + " is out of storage!", uuid);
    }
}
