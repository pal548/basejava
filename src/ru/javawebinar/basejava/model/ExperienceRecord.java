package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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

    public Link getCompany() {
        return company;
    }

    public List<ExperienceSubRecord> getListExperience() {
        return listExperience;
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