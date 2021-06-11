package ru.progwards.java2.lessons.less9;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class Person {
    private String name;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
    public Person() {
        name = "no name";
    }

    private Person(String name) {
        this.name = name;
    }
    static Person callConstructor(String name) {
        Class<?> clazz = Person.class;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            return (Person)constructor.newInstance(name);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) /*throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException*/ {
        System.out.println("\n********************************");
        Person person=new Person("Оля");
        System.out.println(new Person().callConstructor("Вася"));
        System.out.println("********************************");
    }
}
