package ru.progwards.java2.lessons.reflection;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class GettersAndSetters {
    public static void main(String[] args) throws IOException {

        System.out.println(check("ru.progwards.java2.lessons.reflection.TestMethod"));
    }

    @Override
    public String toString() {
        return "GettersAndSetters{}";
    }

    public static String check(String clazz) throws IOException {
        try {
            Class<?> aClass = Class.forName(clazz);
            privateFieldSearch(aClass);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return clazz;
    }

    // метод перебирает поля класса
    private static void privateFieldSearch(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            int mod = f.getModifiers();
            // если модификатор поля "private", то перебираем методы
            if (Modifier.toString(mod).equals("private")) {
                methodSearch(clazz, f);
            }
        }
    }

    // метод ищет методы get & set для данного поля
    // если какого-то из методов нет, то выводит сигнатуру метода на консоль
    private static void methodSearch(Class<?> clazz, Field f) {
        Method[] methods = clazz.getDeclaredMethods();
        boolean existGet = false;
        boolean existSet = false;
        for (Method m : methods) {
            if (m.getName().equals("get" + firstCharInUpperCase(f.getName())) &&
                    m.getReturnType().getSimpleName().equals(f.getType().getSimpleName()) &&
                    Modifier.isPublic(m.getModifiers())) {
                existGet = true;
                continue;
            }
            if (m.getName().equals("set" + firstCharInUpperCase(f.getName())) &&
                    m.getReturnType().getSimpleName().equals("void") &&
                    Modifier.isPublic(m.getModifiers())) {
                existSet = true;
                continue;
            }
            if (existGet && existSet)
                break;
        }
        printMethods(existGet, existSet, f);
    }

    // метод выводит на консоль сигнатуру метода set или get, если его не нашлось в классе
    private static void printMethods(boolean existGet, boolean existSet, Field f) {
        if (!existGet) {
            System.out.println("public " + f.getType().getSimpleName() + " get" + firstCharInUpperCase(f.getName()) +
                    "()");
        }
        if (!existSet) {
            System.out.println("public void set" + firstCharInUpperCase(f.getName()) + "(" +
                    f.getType().getSimpleName() + " " + f.getName() + ")");
        }
    }

    private static String firstCharInUpperCase(String str) {
        String firstChar = str.substring(0, 1).toUpperCase();
        return firstChar + str.substring(1);
    }

}
