package ru.progwards.java2.lessons.tests.test.calc;

public class IntOverflowExc extends RuntimeException {
    int a;
    int b;

    public IntOverflowExc(String message) {
        super(message);
        this.a = a;
        this.b = b;
    }
}

