package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.util.Objects;

public class Experience {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private String title;
    private String description;

    public Experience(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate mst not be null");
        Objects.requireNonNull(endDate, "endDate mst not be null");
        Objects.requireNonNull(title, "title mst not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        String date = DateUtil.format(startDate) + " - " + DateUtil.format(endDate);
        if (description == null) {
            return date + "\n" + title;
        }
        return date + "\n" + title + "\n" + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                title.equals(that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }
}