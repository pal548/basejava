package ru.javawebinar.basejava.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

    @Override
    public void writeToDataStream(DataOutputStream dos) throws IOException {
        dos.writeInt(experienceList.size());
        for(ExperienceRecord er : experienceList) {
            er.writeToDataStream(dos);
        }
    }

    @Override
    public void readFromDataStream(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            ExperienceRecord er = new ExperienceRecord();
            er.readFromDataStream(dis);
            experienceList.add(er);
        }
    }
}
