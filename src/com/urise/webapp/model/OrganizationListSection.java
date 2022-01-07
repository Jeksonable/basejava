package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationListSection extends AbstractSection {
    private List<Organization> organizations;

    public OrganizationListSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must be not null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    public void addExperience(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        organizations.forEach((organization) -> sb.append("\n").append(organization));
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationListSection that = (OrganizationListSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }
}
