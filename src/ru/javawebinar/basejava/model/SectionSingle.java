package ru.javawebinar.basejava.model;

import java.util.Objects;

public class SectionSingle extends AbstractSectionData {
    private static final long serialVersionUID = 1L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionSingle that = (SectionSingle) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
