package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) {

        File file = new File(".gitinore");
        try{
            System.out.println(file.getCanonicalPath());
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        File dir = new File("src\\ru\\javawebinar\\basejava");
        String[] list = dir.list();
        if (list != null) {
            for(String s : list) {
                System.out.println(s);
            }
        }

        try (final FileInputStream fis = new FileInputStream(".gitignore")) {
            System.out.println(fis.read());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
