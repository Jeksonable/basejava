package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage {
    Strategy strategy;

    protected FileStorage(File directory, Strategy strategy) {
        super(directory);
        this.strategy = strategy;
    }

    @Override
    protected void writeResume(Resume r, OutputStream os) throws IOException {
        strategy.writeResume(r, os);
    }

    @Override
    protected Resume readResume(InputStream is) throws IOException {
        return strategy.readResume(is);
    }
}
