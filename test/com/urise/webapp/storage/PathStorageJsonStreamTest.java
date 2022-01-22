package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.JsonStreamStrategy;

public class PathStorageJsonStreamTest extends AbstractStorageTest {

    public PathStorageJsonStreamTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamStrategy()));
    }
}