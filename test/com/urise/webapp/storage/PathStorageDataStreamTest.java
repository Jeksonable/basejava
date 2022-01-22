package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.DataStreamStrategy;

public class PathStorageDataStreamTest extends AbstractStorageTest {

    public PathStorageDataStreamTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamStrategy()));
    }
}