package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.AlreadyExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    protected static final Resume r1 = new Resume(UUID_1, "Иванов");
    protected static final Resume r2 = new Resume(UUID_2, "Петров");
    protected static final Resume r3 = new Resume(UUID_3, "Аверьянов");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void fillResume() {
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
        er.setCompany("Компания 4");
        er.addExperience(LocalDate.of(2016, 10, 1), null, "Старший программист", "--текст описания--");

        SectionExperience sectionExperience = new SectionExperience();
        sectionExperience.addRecord(er);

        er = new ExperienceRecord();
        er.setCompany("Компания 3");
        er.addExperience(LocalDate.of(2014, 1, 1), LocalDate.of(2016, 10, 1), "Архитектор", "--текст описания--");
        sectionExperience.addRecord(er);

        r1.addSection(SectionType.EXPERIENCE, sectionExperience);

        r1.addSection(SectionType.EDUCATION, new SectionSingle("--текст образования--"));
    }
    @Test
    public void testGetSave() throws Exception {
        Resume r = new Resume("Robinson", null);
        storage.save(r);
        assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = AlreadyExistsException.class)
    public void saveAlreadyExists() {
        Resume r = new Resume(UUID_1, "Иванов");
        storage.save(r);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        storage.delete(r1.getUuid());
        storage.get(r1.getUuid());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotExisted() {
        Resume r = new Resume("Jameson", null);
        storage.delete(r.getUuid());
    }

    @Test
    public void update() throws Exception {
        Resume r = new Resume(UUID_1, "Grisham");
        storage.update(r);
        assertTrue(storage.get(r.getUuid()) == r);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(storage.size(), list.size());
        assertEquals(Arrays.asList(r3, r1, r2), list);
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }
}
