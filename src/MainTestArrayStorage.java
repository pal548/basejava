/**
 * Test for com.urise.webapp.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.uuid = "uuid1";
        Resume r2 = new Resume();
        r2.uuid = "uuid2";
        Resume r3 = new Resume();
        r3.uuid = "uuid3";

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.uuid));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();

        ARRAY_STORAGE.delete(r1.uuid);
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
