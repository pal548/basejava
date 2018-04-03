package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.util.UUID;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume>{
    // Unique identifier
    private final String uuid;
    private final String fullName;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
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

    public int compareByFullName(Resume o) {
        int result = fullName.compareTo(o.fullName);
        if (result != 0) {
            return result;
        } else {
            result = uuid.compareTo(o.uuid);
            return result;
        }
    }

    public String getFullName() {
        return fullName;
    }
}
