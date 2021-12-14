package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел.: "),
    SKYPE("Skype: "),
    MAIL("Почта: "),
    LINKED(""),
    GITHUB(""),
    STACK(""),
    HOME("");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
