package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {

    private static void printFileList(File dir) {
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File f : fileList) {
                if (!f.isDirectory()) {
                    System.out.println(f.getName());
                }
                if (f.isDirectory()) {
                    printFileList(f);
                }
            }
        }
    }

    public static void main(String[] args) {

        File file = new File(".gitinore");
        try{
            System.out.println(file.getCanonicalPath());
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        File dir = new File(".\\");
        printFileList(dir);

        try (final FileInputStream fis = new FileInputStream(".gitignore")) {
            System.out.println(fis.read());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
