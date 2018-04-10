package ru.javawebinar.basejava.model;

import java.util.List;

public class SectionExperience extends AbstractSectionData {
    private List<ExperienceRecord> experienceList;

    public SectionExperience(List<ExperienceRecord> experienceList) {
        this.experienceList = experienceList;
    }

    @Override
    public String toString() {
        return experienceList.toString();
    }
}
