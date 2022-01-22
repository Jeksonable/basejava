package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел. : "),
    SKYPE("Skype : "),
    MAIL("Почта : "),
    LINKED("Профиль LinkedIn : "),
    GITHUB("Профиль GitHub : "),
    STACK("Профиль Stackoverflow : "),
    HOME("Домашняя страница : ");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
