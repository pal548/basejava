package ru.javawebinar.basejava.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExperienceRecord {
    private String company;

    private Date dateBeg;

    private Date dateEnd;

    private String position;

    private String description;

    public ExperienceRecord() {
    }

    public ExperienceRecord(String company, Date dateBeg, Date dateEnd, String position, String description) {
        this.company = company;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.position = position;
        this.description = description;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDateBeg(Date dateBeg) {
        this.dateBeg = dateBeg;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
        return company + ", " + df.format(dateBeg) + " - " + (dateEnd != null ? df.format(dateEnd) : " сейчас") + ", " + position + ", " + description;
    }
}