package ru.javawebinar.basejava.model;

import java.util.List;

public class SectionMultiple extends AbstractSectionData {
    private final List<String> strings;

    public SectionMultiple(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public String toString() {
        return strings.toString();
    }
}
