package ru.progwards.java2.lessons.tests.calc;

public class SimpleCalculator {

    public int sum(int val1, int val2) {
        long a = (long) val1 + val2;
        if (a > Integer.MAX_VALUE || a < Integer.MIN_VALUE) throw new ArithmeticException("integers is over");
        return (int) a;
    }

    public int diff(int val1, int val2) {
        long a = (long) val1 - val2;
        if (a > Integer.MAX_VALUE || a < Integer.MIN_VALUE) throw new ArithmeticException("integers is over");
        return (int) a;
    }

    public int mult(int val1, int val2) {
        long a = (long) val1 * val2;
        if (a > Integer.MAX_VALUE || a < Integer.MIN_VALUE) throw new ArithmeticException("integers is over");
        return (int) a;
    }

    public int div(int val1, int val2) {
        if (val2 == 0) throw new ArithmeticException("cannot be divided by zero");
        long a = (long) val1 / val2;
        if (a > Integer.MAX_VALUE || a < Integer.MIN_VALUE) throw new ArithmeticException("integers is over");
        return (int) a;
    }
}

