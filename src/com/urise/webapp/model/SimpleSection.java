package com.urise.webapp.model;

public class SimpleSection extends AbstractSection {
    private String description;

    public SimpleSection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\n" + description;
    }
}
