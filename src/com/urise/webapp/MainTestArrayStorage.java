package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.Storage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "");
        Resume r2 = new Resume("uuid2", "");
        Resume r3 = new Resume("uuid3", "");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

//        ARRAY_STORAGE.update(r1);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.update(r1);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);
        printAll();

        ARRAY_STORAGE.clear();
        ARRAY_STORAGE.save(new Resume("Иван Иванов"));
        ARRAY_STORAGE.save(new Resume("Иван Иванов"));
        ARRAY_STORAGE.save(new Resume("Андрей Андреев"));
        ARRAY_STORAGE.save(new Resume("Иван Иванов"));
        ARRAY_STORAGE.save(new Resume("Сидор Сидоров"));
        ARRAY_STORAGE.save(new Resume("Петр Петров"));
        printAll();
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
