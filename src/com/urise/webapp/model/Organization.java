package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization extends AbstractSection {
    private List<Experience> experiences;

    public Organization(List<Experience> experiences) {
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
        experiences.forEach((experience) -> sb.append("\n").append(experience));
        return sb.toString();
    }
}
