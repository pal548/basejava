package ru.javawebinar.basejava;

import com.sun.org.apache.xpath.internal.SourceTree;
import ru.javawebinar.basejava.exception.AlreadyExistsException;
import ru.javawebinar.basejava.exception.NotFoundException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ListStorage;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Test for ru.javawebinar.basejava.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(new Resume("4"));
        ARRAY_STORAGE.save(new Resume("9"));
        ARRAY_STORAGE.save(new Resume("7"));
        ARRAY_STORAGE.save(new Resume("8"));
        ARRAY_STORAGE.save(new Resume("3"));
        ARRAY_STORAGE.save(new Resume("uuid4"));


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

        System.out.println("Trying to update resume with uuid \"uuid4\"");
        try {
            ARRAY_STORAGE.update(new Resume("uuid4"));
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        printAll();

        ARRAY_STORAGE.update(new Resume("uuid2"));
        System.out.println("Updated uuid2");
        printAll();

        ARRAY_STORAGE.clear();
        System.out.println("Cleared");
        printAll();

        System.out.println("Trying to fill entire storage");
        for(int i = 1; i <= 10000; i++) {
            try {
                ARRAY_STORAGE.save(new Resume(String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000))));
                //ARRAY_STORAGE.save(new Resume());
            } catch (AlreadyExistsException e) {
                System.out.println(e.getMessage());
            }

        }
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("Get All:");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
        System.out.println("");
    }
}
