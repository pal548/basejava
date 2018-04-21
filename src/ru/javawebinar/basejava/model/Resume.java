package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    private final String uuid;
    private final String fullName;

    private Map<SectionType, AbstractSectionData> sections = new HashMap<>();
    private static final List<SectionType> sectionOrder = Arrays.asList(
            SectionType.PERSONAL,
            SectionType.OBJECTIVE,
            SectionType.ACHIEVEMENT,
            SectionType.QUALIFICATIONS,
            SectionType.EXPERIENCE,
            SectionType.EDUCATION);

    private Map<ContactType, String> contacts = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addSection(SectionType type, AbstractSectionData data) {
        sections.put(type, data);
    }

    public void addContact(ContactType type, String s) {
        contacts.put(type, s);
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.getUuid());
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(sections, resume.sections) &&
                Objects.equals(contacts, resume.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections, contacts);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sections=" + sections +
                ", contacts=" + contacts +
                '}';
    }
}
