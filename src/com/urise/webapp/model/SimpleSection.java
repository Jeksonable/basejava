package com.urise.webapp.model;

public class SimpleSection extends AbstractSection {
    private String description;

    public SimpleSection(String title) {
        super(title);
    }

    public SimpleSection(String title, String description) {
        super(title);
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
        String str = description == null ? "" : ("\n" + description);
        return "\n" + title + str;
    }
}
