package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DataReadConsumer;
import com.urise.webapp.util.DataWriteConsumer;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataStreamStrategy implements Strategy {
    @Override
    public void writeResume(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeWithException(r.getContacts().entrySet(), dos, contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });
            writeWithException(r.getSections().entrySet(), dos, section -> {
                SectionType st = section.getKey();
                dos.writeUTF(st.name());
                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        SimpleSection ss = (SimpleSection) section.getValue();
                        dos.writeUTF(ss.getDescription());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        BulletedListSection bls = (BulletedListSection) section.getValue();
                        writeWithException(bls.getDescriptions(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationListSection ols = (OrganizationListSection) section.getValue();
                        writeWithException(ols.getOrganizations(), dos, organization -> {
                            Link link = organization.getHomePage();
                            dos.writeUTF(link.getName());
                            dos.writeUTF(writeOptionalParam(link.getUrl()));
                            writeWithException(organization.getExperiences(), dos, experience -> {
                                dos.writeUTF(getString(experience.getStartDate()));
                                dos.writeUTF(getString(experience.getEndDate()));
                                dos.writeUTF(experience.getTitle());
                                dos.writeUTF(writeOptionalParam(experience.getDescription()));
                            });
                        });
                }
            });
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType st = SectionType.valueOf(dis.readUTF());
                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(st, new SimpleSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> descriptions = new ArrayList<>();
                        readWithException(dis, () -> descriptions.add(dis.readUTF()));
                        resume.addSection(st, new BulletedListSection(descriptions));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        readWithException(dis, () -> {
                            Link link = new Link(dis.readUTF(), readOptionalParam(dis.readUTF()));
                            List<Organization.Experience> experiences = new ArrayList<>();
                            readWithException(dis, () -> experiences.add(
                                    new Organization.Experience(getLocalDate(dis.readUTF()),
                                            getLocalDate(dis.readUTF()),
                                            dis.readUTF(), readOptionalParam(dis.readUTF()))));
                            organizations.add(new Organization(link, experiences));
                        });
                        resume.addSection(st, new OrganizationListSection(organizations));
                }
            });
            return resume;
        }
    }

    private String getString(LocalDate ld) {
        return ld.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private LocalDate getLocalDate(String date) {
        return LocalDate.parse(date);
    }

    private String writeOptionalParam(String param) {
        return param == null ? "null" : param;
    }

    private String readOptionalParam(String param) {
        return param.equals("null") ? null : param;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, DataWriteConsumer<T> action) throws IOException {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(dos);
        Objects.requireNonNull(action);
        dos.writeInt(collection.size());
        for (T t : collection) {
            action.write(t);
        }
    }

    private void readWithException(DataInputStream dis, DataReadConsumer action) throws IOException {
        Objects.requireNonNull(dis);
        Objects.requireNonNull(action);
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            action.read();
        }
    }
}
