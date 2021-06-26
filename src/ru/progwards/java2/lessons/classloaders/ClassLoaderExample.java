package ru.progwards.java2.lessons.classloaders;

public class ClassLoaderExample {
    public static void main(String[] args) {
        // Встроенные классы jrt, загружаются BootstrapClassLoader
        System.out.println(String.class.getClassLoader());
        // Неосновные классы Java API, загружаются PlatformClassLoader
        System.out.println(java.sql.Connection.class.getClassLoader());
        // Обычные классы, загружаются AppClassLoader загрузчиком
        System.out.println(ClassLoaderExample.class.getClassLoader());
        System.out.println(StaticInitBlock.class.getClassLoader());
        System.out.println(ClassLoadOrder.class.getClassLoader());
    }
}
