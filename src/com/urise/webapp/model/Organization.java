package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final Contact homePage;
    private List<Experience> content;

    public Organization(String title, String url, List<Experience> content) {
        Objects.requireNonNull(content, "content mst not be null");
        this.homePage = new Contact(title, url);
        this.content = content;
    }

    public Contact getHomePage() {
        return homePage;
    }

    public List<Experience> getContent() {
        return content;
    }

    public void addContent(Experience value) {
        content.add(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(homePage);
        for (Experience ld : content) {
            sb.append("\n").append(ld);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) &&
                content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, content);
    }
}
