package ru.javawebinar.basejava.model;

public class SectionRecord {
    private SectionType section;
    private AbstractSectionData data;

    public SectionRecord(SectionType section, AbstractSectionData data) {
        this.section = section;
        this.data = data;
    }

    public AbstractSectionData getData() {
        return data;
    }

    public SectionType getSection() {
        return section;
    }
}
