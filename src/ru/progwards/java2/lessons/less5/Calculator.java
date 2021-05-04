package ru.progwards.java2.lessons.less5;

public class Calculator {
    public static int sum(int a, int b) throws IntOverflowException {
        long longRes = ((long)a) + b;
        if (longRes > Integer.MAX_VALUE || longRes < Integer.MIN_VALUE)
            throw new IntOverflowException(a, b, "Переполнение диапазона int при сложении " + longRes);
        return a + b;
    }

    public static int div(int a, int b) {
        return a / b;
    }

    public static void main(String[] args) {
    }
}
