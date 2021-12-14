package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class BulletedListSection extends AbstractSection {
    private List<String> descriptions;

    public BulletedListSection(String title) {
        super(title);
    }

    public BulletedListSection(String title, List<String> descriptions) {
        super(title);
        this.descriptions = descriptions;
    }

    public List<String> getDescriptions() {
        return new ArrayList<>(descriptions);
    }

    public void addDescription(String description) {
        descriptions.add(description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(title);
        descriptions.forEach((description) -> sb.append("\n").append(description));
        return sb.toString();
    }
}
