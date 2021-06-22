package ru.progwards.java2.lessons.less10;

import java.lang.reflect.Method;

public class ResearchAnnotation {
    public static void main(String[] args) {
        Method[] methods = Employee.class.getDeclaredMethods();
        for (Method m: methods) {
            if(m.isAnnotationPresent(First.class)){
                First annotation = m.getAnnotation(First.class);
                System.out.println(annotation.name());
                System.out.println(annotation.value());
            }
        }
    }
}

