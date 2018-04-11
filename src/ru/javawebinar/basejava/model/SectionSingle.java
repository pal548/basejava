package ru.javawebinar.basejava.model;

public class SectionSingle extends AbstractSectionData {
    private String value;

    public SectionSingle(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
