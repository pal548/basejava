package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.model.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    private static final String UUID_1 = "5e80094f-7011-4512-a0d1-ed993d6d039e";
    private static final String UUID_2 = "6bf12e87-6a4a-47d6-822a-a8117f430a20";
    private static final String UUID_3 = "3e4bach2-4277-42bc-ad78-4147d625fa73";

    protected static final Resume R1 = new Resume(UUID_1, "Иванов");
    protected static final Resume R2 = new Resume(UUID_2, "Петров");
    protected static final Resume R3 = new Resume(UUID_3, "Аверьянов");

    static {
        /*R1.addContact(ContactType.EMAIL, "asdfypret@gmail.com");
        R1.addContact(ContactType.PHONE, "+7(903)134-82-43");
        R1.addContact(ContactType.SKYPE, "fasdfasf");
        R1.addContact(ContactType.VK, "asdfadsf");
        R1.addContact(ContactType.FB, "asdfadsf");


        R1.addSection(SectionType.PERSONAL, new SectionSingle("--текст личных качеств--"));
        R1.addSection(SectionType.OBJECTIVE, new SectionSingle("--текст позиции--"));

        SectionMultiple sm = new SectionMultiple();
        sm.addText("--достижение 1--");
        sm.addText("--достижение 2--");
        sm.addText("--достижение 3--");
        R1.addSection(SectionType.ACHIEVEMENT, sm);

        sm = new SectionMultiple();
        sm.addText("--квалификация 1--");
        sm.addText("--квалификация 2--");
        sm.addText("--квалификация 3--");
        R1.addSection(SectionType.QUALIFICATIONS, sm);

        ExperienceRecord er = new ExperienceRecord();
        er.setCompany(new Link("Компания 4", "http://company4.ru"));
        er.addExperience(LocalDate.of(2016, 10, 1), DateUtil.NOW, "Старший программист", "--текст описания--");

        SectionExperience sectionExperience = new SectionExperience();
        sectionExperience.addRecord(er);

        er = new ExperienceRecord();
        er.setCompany(new Link("Компания 3", "http://company3.ru"));
        er.addExperience(LocalDate.of(2014, 1, 1), LocalDate.of(2016, 10, 1), "Архитектор", "--текст описания--");
        sectionExperience.addRecord(er);

        R1.addSection(SectionType.EXPERIENCE, sectionExperience);

        R1.addSection(SectionType.EDUCATION, new SectionSingle("--текст образования--"));*/
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void testGetSave() throws Exception {
        assertEquals(R1, storage.get(R1.getUuid()));
    }

    @Test(expected = ExistsException.class)
    public void saveAlreadyExists() {
        Resume r = new Resume(UUID_1, "Иванов");
        storage.save(r);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        storage.delete(R1.getUuid());
        storage.get(R1.getUuid());
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
        assertTrue(storage.get(r.getUuid()).equals(r));
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(storage.size(), list.size());
        assertEquals(Arrays.asList(R3, R1, R2), list);
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }
}
