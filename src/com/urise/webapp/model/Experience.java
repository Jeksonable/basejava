package com.urise.webapp.model;

import java.util.List;

public class Experience {
    private final String title;
    private String url;
    private List<LocalDate> content;

    public Experience(String title, String url, List<LocalDate> content) {
        this.title = title;
        this.url = url;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public List<LocalDate> getContent() {
        return content;
    }

    public void addContent(LocalDate value) {
        content.add(value);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(title);
        for (LocalDate ld : content) {
            sb.append("\n").append(ld);
        }
        return sb.toString();
    }
}
