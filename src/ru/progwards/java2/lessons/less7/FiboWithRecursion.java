package ru.progwards.java2.lessons.less7;

import java.math.BigInteger;

public class FiboWithRecursion {
    BigInteger prev;
    BigInteger current;
    public FiboWithRecursion(BigInteger prev, BigInteger current) throws NullPointerException{
        this.prev = prev;
        this.current = current;
    }

    public FiboWithRecursion(int i, int i1) {
    }

    FiboWithRecursion next() {
        return new FiboWithRecursion( current, BigInteger.valueOf(prev.intValue()+( current.intValue())));
    }
    @Override
    public String toString() {
        return String.valueOf(current);
    }
    public static FiboWithRecursion getFibo(BigInteger num) {
        switch ((num.intValue())){ // условие выхода
            case 1: return new FiboWithRecursion( BigInteger.ZERO, BigInteger.ONE);
            case 2: return new FiboWithRecursion( BigInteger.ONE, BigInteger.ONE);
        }
        // рекурсия
        FiboWithRecursion preFibo = getFibo(num.subtract(BigInteger.ONE));
        return preFibo.next();
    }
    public static void main(String[] args) {
        FiboWithRecursion fwr = FiboWithRecursion. getFibo(BigInteger.valueOf(44));
        System.out.println(fwr);
    }

}