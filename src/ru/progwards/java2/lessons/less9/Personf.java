package ru.progwards.java2.lessons.less9;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

 class Personf{
    private String name;


    @Override
    public String toString() {
        return "Personf{" +
                "name='" + name + '\'' +
                '}';
    }
     Personf(String name) {
        this.name = name;
    }

    private void setName(String name) {
        this.name = name;
    }

    static String callSetName(Personf person, String name) {
        Class<?> clazz = person.getClass();
        try {
            Method method = clazz.getDeclaredMethod("setName", String.class);
            method.setAccessible(true);
            method.invoke(person, (Object) name);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return name;
    }

    public static void main(String[] args) {
        System.out.println("\n********************************");
        Personf person = new Personf("Федя");
        System.out.println(new Personf("Fedya").callSetName(person, "Саша"));
        System.out.println("********************************");
    }
    }



