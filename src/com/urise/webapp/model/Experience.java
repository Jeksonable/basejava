package com.urise.webapp.model;

import java.util.Map;

public class Experience {
    private final String title;
    private String url;
    private Map<String, SimpleSection> content;

    public Experience(String title) {
        this.title = title;
    }

    public Experience(String title, String url, Map<String, SimpleSection> content) {
        this.title = title;
        this.url = url;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, SimpleSection> getContent() {
        return content;
    }

    public void addContent(String key, SimpleSection value) {
        content.put(key, value);
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
        for (Map.Entry<String, SimpleSection> map : content.entrySet()) {
            sb.append("\n").append(map.getKey()).append(map.getValue());
        }
        return sb.toString();
    }
}
