package com.urise.webapp.storage;

import static org.junit.Assert.*;

public class PathStorageObjectStreamTest extends AbstractStorageTest {

    public PathStorageObjectStreamTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStrategy()));
    }
}