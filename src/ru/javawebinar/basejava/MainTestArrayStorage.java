package ru.javawebinar.basejava;

import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.ListStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Test for ru.javawebinar.basejava.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("Name1");

        r1.addContact(ContactType.EMAIL, "asdfypret@gmail.com");
        r1.addContact(ContactType.PHONE, "+7(903)134-82-43");
        r1.addContact(ContactType.SKYPE, "fasdfasf");
        r1.addContact(ContactType.VK, "asdfadsf");
        r1.addContact(ContactType.FB, "asdfadsf");


        r1.addSection(SectionType.PERSONAL, new SectionSingle("--текст личных качеств--"));
        r1.addSection(SectionType.OBJECTIVE, new SectionSingle("--текст позиции--"));

        SectionMultiple sm = new SectionMultiple();
        sm.addText("--достижение 1--");
        sm.addText("--достижение 2--");
        sm.addText("--достижение 3--");
        r1.addSection(SectionType.ACHIEVEMENT, sm);

        sm = new SectionMultiple();
        sm.addText("--квалификация 1--");
        sm.addText("--квалификация 2--");
        sm.addText("--квалификация 3--");
        r1.addSection(SectionType.QUALIFICATIONS, sm);

        ExperienceRecord er = new ExperienceRecord();
        er.setCompany(new Link("Компания 4", "http://company4.ru"));
        er.addExperience(LocalDate.of(2016, 10, 1), null, "Старший программист", "--текст описания--");

        SectionExperience sectionExperience = new SectionExperience();
        sectionExperience.addRecord(er);

        er = new ExperienceRecord();
        er.setCompany(new Link("Компания 3", "http://company3.ru"));
        er.addExperience(LocalDate.of(2014, 1, 1), LocalDate.of(2016, 10, 1), "Архитектор", "--текст описания--");
        sectionExperience.addRecord(er);

        r1.addSection(SectionType.EXPERIENCE, sectionExperience);

        r1.addSection(SectionType.EDUCATION, new SectionSingle("--текст образования--"));


        //Resume r2 = new Resume("Name2", sections);
        //Resume r3 = new Resume("Name3", sections);

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.save(r1);
        /*ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(new Resume("4", sections));
        ARRAY_STORAGE.save(new Resume("9", sections));
        ARRAY_STORAGE.save(new Resume("7", sections));
        ARRAY_STORAGE.save(new Resume("8", sections));
        ARRAY_STORAGE.save(new Resume("3", sections));
        ARRAY_STORAGE.save(new Resume("uuid4", sections));*/


        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        Resume r = null;
        try {
            r = ARRAY_STORAGE.get("dummy");
        } catch (NotFoundException e) {
            System.out.println("dummy not found");
        }
        System.out.println("Get dummy: " + r);

        printAll();

        ARRAY_STORAGE.delete(r1.getUuid());
        System.out.println("Deleted r1");
        printAll();

        System.out.println("Trying to delete resume with uuid \"uuid4\"");
        try {
            ARRAY_STORAGE.delete("uuid4");
        } catch (NotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
        printAll();

        /*System.out.println("Trying to update resume with uuid \"uuid4\"");
        try {
            ARRAY_STORAGE.update(new Resume("uuid4", sections));
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        printAll();

        ARRAY_STORAGE.update(new Resume("uuid2", sections));
        System.out.println("Updated uuid2");
        printAll();

        ARRAY_STORAGE.clear();
        System.out.println("Cleared");
        printAll();

        System.out.println("Trying to fill entire storage");
        for(int i = 1; i <= 10000; i++) {
            try {
                ARRAY_STORAGE.save(new Resume(String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000)), UUID.randomUUID().toString(), sections));
                //ARRAY_STORAGE.save(new Resume());
            } catch (ExistsException e) {
                System.out.println(e.getMessage());
            }

        }
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
        */
    }

    static void printAll() {
        System.out.println("Get All:");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
        System.out.println("");
    }
}
