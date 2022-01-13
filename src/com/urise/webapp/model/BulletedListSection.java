package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BulletedListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> descriptions;

    public BulletedListSection(String... descriptions) {
        this(Arrays.asList(descriptions));
    }

    public BulletedListSection(List<String> descriptions) {
        Objects.requireNonNull(descriptions, "descriptions must be not null");
        this.descriptions = descriptions;
    }

    public List<String> getDescriptions() {
        return new ArrayList<>(descriptions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        descriptions.forEach((description) -> sb.append("\n").append(description));
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulletedListSection that = (BulletedListSection) o;
        return descriptions.equals(that.descriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptions);
    }
}
