package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SectionExperience extends AbstractSectionData {
    private static final long serialVersionUID = 1L;

    private List<ExperienceRecord> experienceList = new ArrayList<>();

    public SectionExperience() {
    }

    public void addRecord(ExperienceRecord r) {
        experienceList.add(r);
    }

    @Override
    public String toString() {
        return experienceList.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionExperience that = (SectionExperience) o;
        return Objects.equals(experienceList, that.experienceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experienceList);
    }
}
