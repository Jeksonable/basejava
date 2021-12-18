package com.urise.webapp.model;

public class LocalDate {
    private String date;
    private String title;
    private String description;

    public LocalDate(String date, String title) {
        this.date = date;
        this.title = title;
        this.description = "";
    }

    public LocalDate(String date, String title, String description) {
        this.date = date;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        if (description.isEmpty()) {
            return date + "\n" + title;
        }
        return date + "\n" + title + "\n" + description;
    }
}