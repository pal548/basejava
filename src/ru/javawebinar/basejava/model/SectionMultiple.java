package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class SectionMultiple extends AbstractSectionData {
    private List<String> strings= new ArrayList<>();

    public SectionMultiple() {
    }

    public void addText(String s) {
        strings.add(s);
    }

    @Override
    public String toString() {
        return strings.toString();
    }
}
