package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExperienceSubRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateBeg;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateEnd;

    private String position;

    private String description;

    public ExperienceSubRecord() {
    }

    public ExperienceSubRecord(LocalDate dateBeg, LocalDate dateEnd, String position, String description) {
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.position = position;
        this.description = description;
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

    public LocalDate getDateBeg() {
        return dateBeg;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/yyyy");
        return df.format(dateBeg) + " - " + (dateEnd != null ? df.format(dateEnd) : " сейчас") + ", " + position + ", " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSubRecord that = (ExperienceSubRecord) o;

        if (!dateBeg.equals(that.dateBeg)) return false;
        if (dateEnd != null ? !dateEnd.equals(that.dateEnd) : that.dateEnd != null) return false;
        if (!position.equals(that.position)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = dateBeg.hashCode();
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + position.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
