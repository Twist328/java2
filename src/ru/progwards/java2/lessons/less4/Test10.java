package ru.progwards.java2.lessons.less4;

public class Test10 {
    static int N = 13;
    static final double A = 0.6180339887;

    static int hash(int k) {
        double d = A*k;
        return (int)(N*(d-Math.floor(d)));
    }

    static long RSHash (String str)
    {
        long b = 378551;
        long a = 63689;
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = hash * a + str.charAt(i);
            a = a * b;
        }
        return hash;
    }

    public static void main(String[] args) {
//        System.out.println(hash(25));
//        System.out.println(hash(26));
//        System.out.println(hash(27));
        System.out.println(RSHash("Маша"));
        System.out.println(RSHash("Петя"));
        System.out.println(RSHash("Вася"));
    }
}

