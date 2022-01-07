package com.urise.webapp.model;

import java.util.Objects;

public class Contact {
    private final String name;
    private final String url;

    public Contact(String name, String url) {
        Objects.requireNonNull(name, "name must be not null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        if (url == null || url.isEmpty()) {
            return name;
        }
        return name + '(' + url + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return name.equals(contact.name) &&
                Objects.equals(url, contact.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
