package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class BulletedListSection extends AbstractSection {
    private List<String> descriptions;

    public BulletedListSection(List<String> descriptions) {
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
        descriptions.forEach((description) -> sb.append("\n").append(description));
        return sb.toString();
    }
}
