package ru.progwards.java2.lessons.annotation;

 class Calculator implements CalculatorCalculate {
    @Override
    public int sum(int val1, int val2) {
        long result = (long) val1 + val2;
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE)
            throw new ArithmeticException();
        return val1 + val2;
    }

    @Override
    public int different(int val1, int val2) {
        long result = (long) val1 - val2;
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE)
            throw new ArithmeticException();
        return val1 - val2;
    }

    @Override
    public int multiply(int val1, int val2) {
        long result = (long) val1 * val2;
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE)
            throw new ArithmeticException();
        return val1 * val2;
    }

    @Override
    public int divid(int val1, int val2) {
        if (val2 == 0)
            throw new ArithmeticException();
        return val1 / val2;
    }
}