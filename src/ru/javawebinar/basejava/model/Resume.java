package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume>{
    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final List<SectionRecord> sections;

    public Resume(String fullName, List<SectionRecord> sections) {
        this(UUID.randomUUID().toString(), fullName, sections);
    }

    public Resume(String uuid, String fullName, List<SectionRecord> sections) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.sections = sections;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return uuid + ", " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.getUuid());
    }

    public String getFullName() {
        return fullName;
    }
}
