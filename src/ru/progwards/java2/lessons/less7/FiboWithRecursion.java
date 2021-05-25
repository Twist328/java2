package ru.progwards.java2.lessons.less7;

import java.math.BigInteger;

public class FiboWithRecursion {
    BigInteger prev;
    BigInteger current;

    public FiboWithRecursion(BigInteger prev, BigInteger current) throws NullPointerException {
        this.prev = prev;
        this.current = current;
    }

    public FiboWithRecursion() {

    }

    FiboWithRecursion next() {
        return new FiboWithRecursion(current, prev.add(current));
    }

    @Override
    public String toString() {
        return String.valueOf(current);
    }

    public static FiboWithRecursion getFibo(BigInteger num) {
        switch (num.intValueExact()) { // условие выхода
            case 1:
                return new FiboWithRecursion(BigInteger.ZERO, BigInteger.ONE);
            case 2:
                return new FiboWithRecursion(BigInteger.ONE, BigInteger.ONE);
        }
        // рекурсия
        FiboWithRecursion preFibo = getFibo(num.subtract(BigInteger.ONE));
        return preFibo.next();
    }

    public static void main(String[] args) {
        FiboWithRecursion fwr = FiboWithRecursion.getFibo((BigInteger.valueOf(200)));
        System.out.println(fwr);
        System.out.println("__________");

        FiboWithRecursion fwr1 = new FiboWithRecursion();
        BigInteger first = BigInteger.ONE;
        BigInteger next = BigInteger.ONE;
        BigInteger current1;
        for (int i = 2; i < 200; i++) {
            current1 = first.add(next);
            first = next;
            next = current1;
        }
        System.out.println("Fibonacci's elementh is " + next);

        System.out.println("______________");

        BigInteger prev=BigInteger.ZERO, first1=BigInteger.ONE, next1=BigInteger.ZERO;

        for (int i=0; i<200; i++){
            prev=first1; first1=next1; next1=prev.add(first1);
            System.out.println(next1);
        }
    }
    }

