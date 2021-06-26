package ru.progwards.java2.lessons.less11.classloaders;

public class ClassLoaderGetLoaders {
    public static void main(String[] args) {
        System.out.println(ClassLoader.getPlatformClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());
    }
}

