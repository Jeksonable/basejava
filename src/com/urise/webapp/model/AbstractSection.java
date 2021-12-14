package com.urise.webapp.model;

public abstract class AbstractSection {
    protected final String title;

    public AbstractSection(String description) {
        this.title = description;
    }

    public String getTitle() {
        return title;
    }
}
