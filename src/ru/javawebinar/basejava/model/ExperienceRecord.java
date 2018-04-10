package ru.javawebinar.basejava.model;

public class ExperienceRecord {
    private final String company;

    private final String dates;

    private final String position;

    private final String description;

    public ExperienceRecord(String company, String dates, String position, String description) {
        this.company = company;
        this.dates = dates;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return company + ", " + dates + ", " + position + ", " + description;
    }
}