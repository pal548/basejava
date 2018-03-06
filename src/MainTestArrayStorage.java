import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.SortedArrayStorage;
import ru.javawebinar.storage.Storage;

/**
 * Test for com.urise.webapp.storage.ru.javawebinar.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();

        ARRAY_STORAGE.delete(r1.getUuid());
        System.out.println("Deleted r1");
        printAll();

        System.out.println("Trying to delete resume with uuid \"uuid4\"");
        ARRAY_STORAGE.delete("uuid4");
        printAll();

        System.out.println("Trying to update resume with uuid \"uuid4\" to resume with uuid \"uuid5\"");
        ARRAY_STORAGE.update("uuid4", new Resume("uuid5"));
        printAll();

        ARRAY_STORAGE.update("uuid2", new Resume("uuid4"));
        System.out.println("Updated uuid2 to uuid4");
        printAll();

        ARRAY_STORAGE.clear();
        System.out.println("Cleared");
        printAll();

        System.out.println("Trying to fill entire storage");
        for(int i = 1; i <= 10002; i++) {
            ARRAY_STORAGE.save(new Resume("uuid"+i));
        }


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
