package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class SectionExperience extends AbstractSectionData {
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
}
