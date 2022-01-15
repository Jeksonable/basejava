package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStreamStrategy;

public class FileStorageObjectStreamTest extends AbstractStorageTest {

    public FileStorageObjectStreamTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}