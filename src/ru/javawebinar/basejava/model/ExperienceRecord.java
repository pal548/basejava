package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExperienceRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link company;

    private List<ExperienceSubRecord> listExperience = new ArrayList<>();

    public ExperienceRecord() {
    }

    public void setCompany(Link company) {
        this.company = company;
    }

    public void addExperience(LocalDate startDate, LocalDate endDate, String position, String descriprion) {
        listExperience.add(new ExperienceSubRecord(startDate, endDate, position, descriprion));
    }

    @Override
    public String toString() {
        return company + ", " + listExperience.toString();
    }

    public void writeToDataStream(DataOutputStream dos) throws IOException {
        dos.writeUTF(company.getName());
        dos.writeUTF(company.getUrl());
        dos.writeInt(listExperience.size());
        for(ExperienceSubRecord esb : listExperience) {
            esb.writeToDataStream(dos);
        }
    }

    public void readFromDataStream(DataInputStream dis) throws IOException {
        company = new Link(dis.readUTF(), dis.readUTF());
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            ExperienceSubRecord esb = new ExperienceSubRecord();
            esb.readFromDataStream(dis);
            listExperience.add(esb);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceRecord that = (ExperienceRecord) o;

        if (!company.equals(that.company)) return false;
        return listExperience.equals(that.listExperience);
    }

    @Override
    public int hashCode() {
        int result = company.hashCode();
        result = 31 * result + listExperience.hashCode();
        return result;
    }
}