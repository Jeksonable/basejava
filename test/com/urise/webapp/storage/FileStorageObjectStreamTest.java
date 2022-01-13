package com.urise.webapp.storage;

import static org.junit.Assert.*;

public class FileStorageObjectStreamTest extends AbstractStorageTest {

    public FileStorageObjectStreamTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}