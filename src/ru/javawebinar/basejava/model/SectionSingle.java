package ru.javawebinar.basejava.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class SectionSingle extends AbstractSectionData {
    private static final long serialVersionUID = 1L;

    private String value;

    public SectionSingle() {
    }

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

    @Override
    public void writeToDataStream(DataOutputStream dos) throws IOException {
        dos.writeUTF(value);
    }

    @Override
    public void readFromDataStream(DataInputStream dis) throws IOException {
        value = dis.readUTF();
    }
}
