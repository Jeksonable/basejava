package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.XmlStreamStrategy;

public class PathStorageXmlStreamTest extends AbstractStorageTest {

    public PathStorageXmlStreamTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamStrategy()));
    }
}