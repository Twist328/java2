package ru.progwards.java2.lessons.classloaders;

import ru.progwards.java2.lessons.less11.loadparentclasses.FennecFox;

public class LoadAllClasses {
    static {
        try {
            Class.forName(
                    "ru.progwards.java2.lessons.less11.loadparentclasses.Fox",
                    true,
                    ClassLoader.getSystemClassLoader()
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println("Точка входа");
        System.out.println(new FennecFox().info);
    }
}
