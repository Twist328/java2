package ru.progwards.java2.lessons.less14;

public class InvalidPointerException extends RuntimeException {
    private int ptr;

    public InvalidPointerException(int ptr) {
        this.ptr = ptr;
    }

    @Override
    public String toString() {
        return "Занятого блока памяти с начальным индексом " + ptr + " не существует";
    }
}
