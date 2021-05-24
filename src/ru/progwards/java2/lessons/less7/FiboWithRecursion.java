package ru.progwards.java2.lessons.less7;

import java.math.BigInteger;

public class FiboWithRecursion {
    long prev;
    long current;
    public FiboWithRecursion(long prev, long current) throws NullPointerException{
        this.prev = prev;
        this.current = current;
    }

    FiboWithRecursion next() {
        return new FiboWithRecursion( current, prev +( current));
    }
    @Override
    public String toString() {
        return String.valueOf(current);
    }
    public static FiboWithRecursion getFibo(long num) {
        switch (Math.toIntExact(num)){ // условие выхода
            case 1: return new FiboWithRecursion( 0, 1);
            case 2: return new FiboWithRecursion( 1, 1);
        }
        // рекурсия
        FiboWithRecursion preFibo = getFibo(num-1);
        return preFibo.next();
    }
    public static void main(String[] args) {
        FiboWithRecursion fwr = FiboWithRecursion. getFibo((70));
        System.out.println(fwr);
    }

}