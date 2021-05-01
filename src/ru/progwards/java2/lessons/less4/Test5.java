package ru.progwards.java2.lessons.less4;

import java.util.ArrayDeque;

public class Test5 {
    static final int ITERATIONS = 10000000;

    static void testAdd() {
        long t1 = System.currentTimeMillis();
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for(int i=0; i<ITERATIONS; i++)
            ad.offer(i);
        long t2 = System.currentTimeMillis();
        EList el = new EList();
        for(int i=0; i<ITERATIONS; i++)
            el.add(new Test2.MyItem(i));
        long t3 = System.currentTimeMillis();
        ADeque<Integer> ad2 = new ADeque<>();
        for(int i=0; i<ITERATIONS; i++)
            ad.addFirst(i);
        long t4 = System.currentTimeMillis();

        System.out.println("--- test add ---");
        System.out.println("ArrayDeque = "+(t2-t1));
        System.out.println("EList      = "+(t3-t2));
        System.out.println("ADeque     = "+(t4-t3));

        testGet(ad, el, ad2);
    }

    static void testGet(ArrayDeque<Integer> ad, EList el, ADeque<Integer> ad2) {
        long t1 = System.currentTimeMillis();
        for(int i=0; i<ITERATIONS; i++)
            ad.poll();
        long t2 = System.currentTimeMillis();
        for(int i=0; i<ITERATIONS; i++)
            el.poll();
        long t3 = System.currentTimeMillis();
        for(int i=0; i<ITERATIONS; i++)
            ad2.pollFirst();
        long t4 = System.currentTimeMillis();

        System.out.println("--- test get ---");
        System.out.println("ArrayDeque = "+(t2-t1));
        System.out.println("EList      = "+(t3-t2));
        System.out.println("ADeque     = "+(t4-t3));
    }

    public static void main(String[] args) {
        testAdd();
    }
}

