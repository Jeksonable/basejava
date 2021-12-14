package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization extends AbstractSection {
    private List<Experience> experiences;

    public Organization(String title) {
        super(title);
    }

    public Organization(String title, List<Experience> experiences) {
        super(title);
        this.experiences = experiences;
    }

    public List<Experience> getExperiences() {
        return new ArrayList<>(experiences);
    }

    public void addExperience(Experience experience) {
        experiences.add(experience);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(title);
        experiences.forEach((experience) -> sb.append("\n").append(experience));
        return sb.toString();
    }
}
