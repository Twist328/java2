package ru.progwards.java2.lessons.tests.calc;


import ru.progwards.java2.lessons.tests.test.calc.IntOverflowExc;

public class SimpleCalculator {

    public static int sum(int val1, int val2) throws IntOverflowExc {
        long longRes = ((long)val1) + val2;
        if (longRes > Integer.MAX_VALUE || longRes < Integer.MIN_VALUE)
            throw new IntOverflowExc(val1, val2, "Int range overflow during addition " + longRes);
        return val1 + val2;
    }

    public static int diff(int val1, int val2) throws IntOverflowExc {
        long longRes = ((long)val1) - val2;
        if (longRes > Integer.MAX_VALUE || longRes < Integer.MIN_VALUE)
            throw new IntOverflowExc(val1, val2, "Int range overflow during on subtraction " + longRes);
        return val1 - val2;
    }

    public static int mult(int val1, int val2) {
        long longRes = (long)val1 * val2;
        if (longRes > Integer.MAX_VALUE || longRes < Integer.MIN_VALUE)
            throw new IntOverflowExc(val1, val2, "Int range overflow during on mult " + longRes);
        return val1 * val2;
    }

    public int div(int val1, int val2) {
        if (val2 == 0) throw new ArithmeticException("cannot be divided by zero");
        long longRes = (long) val1 / val2;
        if (longRes > Integer.MAX_VALUE || longRes < Integer.MIN_VALUE) throw new ArithmeticException("integers is over");
        return val1 / val2;
    }
}

