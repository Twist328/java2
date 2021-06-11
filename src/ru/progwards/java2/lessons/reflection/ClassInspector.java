package ru.progwards.java2.lessons.reflection;

import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClassInspector {

    @Override
    public String toString() {
        return "ClassInspector{}";
    }

    public static String inspect(String clazz) throws IOException{
        String result = "";
        try {
            Class<?> aClass = Class.forName(clazz);
            result += "class " + aClass.getSimpleName() + " {\n";
            Field[] fields = aClass.getDeclaredFields();
            result += getInfoFields(fields);
            Constructor<?>[] constructors = aClass.getDeclaredConstructors();
            result += getInfoConstructors(constructors, aClass);
            Method[] methods = aClass.getDeclaredMethods();
            result += getInfoMethods(methods) + "}";
            writeToFile(result, aClass);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private static void writeToFile(String str, Class<?> clazz) {
        Path file = Paths.get(clazz.getSimpleName() + ".java");
        try {
            if (Files.notExists(file))
                Files.createFile(file);
            Files.writeString(file, str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // метод возвращает сигнатуру каждого поля (свойства класса)
    private static String getInfoFields(Field[] fields) {
        String result = "";
        for (Field f : fields) {
            int mod = f.getModifiers();
            if (mod == 0)
                result += "    " + f.getType().getSimpleName() + " " + f.getName() + ";\n";
            else
                result += "    " + Modifier.toString(mod) + " " + f.getType().getSimpleName() + " " +
                        f.getName() + ";\n";
        }
        return result;
    }

    // метод возвращает сигнатуру каждого конструктора
    private static String getInfoConstructors(Constructor<?>[] constructors, Class<?> clazz) {
        String result = "";
        for (Constructor<?> c : constructors) {
            int mod = c.getModifiers();
            if (mod == 0)
                result += "    " + clazz.getSimpleName() + "(" + getInfoParametersConstructor(c) + ")" + " {}\n";
            else
                result += "    " + Modifier.toString(mod) + " " + clazz.getSimpleName() + "(" +
                        getInfoParametersConstructor(c) + ")" + " {}\n";
        }
        return result;
    }

    // метод возвращает сигнатуру каждого параметра конструктора
    private static String getInfoParametersConstructor(Constructor<?> c) {
        Parameter[] parameters = c.getParameters();
        return getInfoParameters(parameters);
    }

    // метод возвращает сигнатуру каждого метода
    private static String getInfoMethods(Method[] methods) {
        String result = "";
        for (Method m : methods) {
            int mod = m.getModifiers();
            if (mod == 0)
                result += "    " + m.getReturnType().getSimpleName() + " " + m.getName() + "(" +
                        getInfoParametersMethod(m) + ") {}\n";
            else
                result += "    " + Modifier.toString(mod) + " " + m.getReturnType().getSimpleName() + " " +
                        m.getName() + "(" + getInfoParametersMethod(m) + ") {}\n";
        }
        return result;
    }

    // метод возвращает сигнатуру каждого параметра метода
    private static String getInfoParametersMethod(Method m) {
        Parameter[] parameters = m.getParameters();
        return getInfoParameters(parameters);
    }

    // метод проходится по каждому параметру
    private static String getInfoParameters(Parameter[] parameters) {
        String result = "";
        int count = 0;
        for (Parameter p : parameters) {
            ++count;
            if (count == parameters.length)
                result += p.getType().getSimpleName() + " " + p.getName();
            else
                result += p.getType().getSimpleName() + " " + p.getName() + ", ";
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new ClassInspector().inspect("ru.progwards.java2.lessons.gc_af.Heap"));
    }

}