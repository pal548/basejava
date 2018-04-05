package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("Jameson", sections);
        Field f = r.getClass().getDeclaredFields()[0];
        f.setAccessible(true);
        System.out.println(f.getName());
        System.out.println(f.get(r));
        f.set(r, "new_uuid");
        Method m = r.getClass().getMethod("toString");
        System.out.println(m.invoke(r));
    }
}
