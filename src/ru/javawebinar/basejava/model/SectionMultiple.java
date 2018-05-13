package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SectionMultiple extends AbstractSectionData {
    private static final long serialVersionUID = 1L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionMultiple that = (SectionMultiple) o;
        return Objects.equals(strings, that.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strings);
    }
}
