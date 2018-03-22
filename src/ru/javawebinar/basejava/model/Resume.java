package ru.javawebinar.basejava.model;

import java.util.UUID;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume>{
    // Unique identifier
    private final String uuid;
    private final String fullName;

    public Resume() {
        this(UUID.randomUUID().toString());
    }
    public Resume(String uuid) {
        this(uuid, "");
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

        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        // взято из https://stackoverflow.com/questions/10587506/creating-a-hash-from-several-java-string-objects
        int result = 17;
        result = 37 * result + uuid.hashCode();
        result = 37 * result + fullName.hashCode();
        return result;
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
