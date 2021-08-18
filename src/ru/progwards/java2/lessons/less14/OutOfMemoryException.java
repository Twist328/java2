package ru.progwards.java2.lessons.less14;

public class OutOfMemoryException extends RuntimeException {
    private int size;

    public OutOfMemoryException(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Свободного блока памяти размером " + size + " не существует";
    }
}