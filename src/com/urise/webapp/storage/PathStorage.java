package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PathStorage extends AbstractPathStorage {
    Strategy strategy;

    protected PathStorage(String dir, Strategy strategy) {
        super(dir);
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
