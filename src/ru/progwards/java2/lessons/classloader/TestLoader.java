package ru.progwards.java2.lessons.classloader;

public class TestLoader {
    private long modifiedTime;

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void printString() {

        System.out.println("\n******************************");
        System.out.println("НОВАЯ строка!!!");
        System.out.println("******************************");
    }
}
