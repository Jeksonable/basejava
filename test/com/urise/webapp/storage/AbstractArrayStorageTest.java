package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_1);
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void save() {
        storage.save(new Resume("setTest"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void get() throws NoSuchFieldException, IllegalAccessException {
        Field field = storage.getClass().getSuperclass().getDeclaredField("storage");
        Resume r = (Resume) Array.get(field.get(storage), 0);
        Assert.assertEquals(r, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid4");
    }

    @Test
    public void getAll() {
        storage.clear();
        Resume r1 = new Resume(UUID_1);
        Resume r2 = new Resume(UUID_2);
        Resume r3 = new Resume(UUID_3);
        Resume[] allResumes = {r1, r2, r3};
        for (Resume resume : allResumes) {
            storage.save(resume);
        }
        Assert.assertArrayEquals(allResumes, storage.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Преждевременное переполнение хранилища резюме");
        }
        storage.save(new Resume());
    }
}