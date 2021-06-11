package ru.progwards.java2.lessons.less9;

import java.lang.reflect.Field;

class PersonM {
    private String name;

    PersonM(String name) {
        this.name = name;
    }
    static String setName(PersonM person, String name) {
        Class clazz = person.getClass();
        try {
            Field field = clazz.getDeclaredField("name");
            field.setAccessible(true);
            field.set(person, name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static void main(String[] args) {
        System.out.println("\n**************************");
        PersonM person = new PersonM("Вася");
        System.out.println(new PersonM("Вася").setName(person, "     Федя"));
        System.out.println("**************************");
    }
}

