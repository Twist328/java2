package ru.progwards.java2.lessons.less11.classloaders;

public class ClassLoadOrder {
    String str = "Какая-то строка";
    static {
        System.out.println("ClassLoadOrder статический блок инициализации");
    }
}


