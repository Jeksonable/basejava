package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements Strategy {
    @Override
    public void writeResume(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        SimpleSection section = (SimpleSection) entry.getValue();
                        dos.writeUTF(section.getDescription());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        BulletedListSection bls = (BulletedListSection) entry.getValue();
                        List<String> descriptions = bls.getDescriptions();
                        dos.writeInt(descriptions.size());
                        for (String description : descriptions) {
                            dos.writeUTF(description);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationListSection ols = (OrganizationListSection) entry.getValue();
                        List<Organization> organizations = ols.getOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                            Link link = organization.getHomePage();
                            dos.writeUTF(link.getName());
                            dos.writeUTF(link.getUrl());
                            List<Organization.Experience> experiences = organization.getExperiences();
                            dos.writeInt(experiences.size());
                            for (Organization.Experience experience : experiences) {
                                dos.writeUTF(experience.getStartDate().toString());
                                dos.writeUTF(experience.getEndDate().toString());
                                dos.writeUTF(experience.getTitle());
                                dos.writeUTF(experience.getDescription());
                            }
                        }
                }
            }
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                SectionType st = SectionType.valueOf(dis.readUTF());
                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(st, new SimpleSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> descriptions = new ArrayList<>();
                        int sizeDescriptions = dis.readInt();
                        for (int j = 0; j < sizeDescriptions; j++) {
                            descriptions.add(dis.readUTF());
                        }
                        resume.addSection(st, new BulletedListSection(descriptions));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        int sizeOrganizations = dis.readInt();
                        for (int j = 0; j < sizeOrganizations; j++) {
                            Link link = new Link(dis.readUTF(), dis.readUTF());
                            List<Organization.Experience> experiences = new ArrayList<>();
                            int sizeExperiences = dis.readInt();
                            for (int k = 0; k < sizeExperiences; k++) {
                                experiences.add(
                                        new Organization.Experience(LocalDate.parse(dis.readUTF()),
                                                LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                            }
                            organizations.add(new Organization(link, experiences));
                        }
                        resume.addSection(st, new OrganizationListSection(organizations));
                }
            }
            return resume;
        }
    }
}
