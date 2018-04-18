package ru.javawebinar.basejava.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExperienceRecord {
    private String company;

    private LocalDate dateBeg;

    private LocalDate dateEnd;

    private String position;

    private String description;

    public ExperienceRecord() {
    }

    public ExperienceRecord(String company, LocalDate dateBeg, LocalDate dateEnd, String position, String description) {
        this.company = company;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.position = position;
        this.description = description;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDateBeg(LocalDate dateBeg) {
        this.dateBeg = dateBeg;
    }

    public void setDateEnd(LocalDate dateEnd) {
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
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/yyyy");
        return company + ", " + df.format(dateBeg) + " - " + (dateEnd != null ? df.format(dateEnd) : " сейчас") + ", " + position + ", " + description;
    }
}