package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    private static final String TAB = "  ";

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String s : list) {
                System.out.println(s);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            e.printStackTrace();
        }

        File root = new File(".");
        int count = 1;
        try {
            System.out.println(root.getCanonicalPath());
            String[] listFiles = root.list();
            if (listFiles != null) {
                for (String s : listFiles) {
                    System.out.println(s);
                    File child = new File(s);
                    if (child.isDirectory()) {
                        printDirFiles(child, count);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
    }

    private static void printDirFiles(File dir, int count) throws IOException {
        String[] listFiles = dir.list();
        if (listFiles != null) {
            for (String s : listFiles) {
                for (int i = 0; i < count; i++) {
                    System.out.print(TAB);
                }
                System.out.println(s);
                File child = new File(dir.getCanonicalPath() + "/" + s);
                if (child.isDirectory()) {
                    count++;
                    printDirFiles(child, count);
                    count--;
                }
            }
        }
    }
}
