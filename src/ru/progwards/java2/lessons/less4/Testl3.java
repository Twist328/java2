package ru.progwards.java2.lessons.less4;

public class Testl3 {


    static long  RSHash(String str) {  //Хеш-функции для строк
        long b = 378551;
        long a = 63689;
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {

            hash =  RSHash(String.valueOf(hash * a + str.charAt(i)));
            a =  RSHash(String.valueOf(a * b));
        }
        return hash;
    }
    static final long UINT_MAX = 4294967295L;
    static long unsignedInt(long num) {
        return num % UINT_MAX;
    }

    public static void main(String[] args) {
        System.out.println(new Testl3(). RSHash(String.valueOf(987985l)));
        System.out.println(new Testl3().unsignedInt(987985l));

    }
}
