package ru.progwards.java2.lessons.less16;

import ru.progwards.java2.lessons.sort.SelectionSort;
        import ru.progwards.java2.lessons.sort.SortTest;

import java.util.Random;
import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

    static final int TEST_COUNT = 2_000;
    static final int POOL_SIZE = 5;

    public static Integer[] org = new Integer[100];

    static class RunSort implements Runnable {
        @Override
        public void run() {
            Integer[] a = SortTest.copy(ThreadTest.org);
            SelectionSort.sort(a);
        }
    }

    static void sortTest() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < TEST_COUNT; i++) {
            new RunSort().run();
        }
        long stop = System.currentTimeMillis();
        System.out.println(stop - start);
    }

    static void sortThreads() throws InterruptedException {
        Thread[] t = new Thread[TEST_COUNT];

        long start = System.currentTimeMillis();
        for (int i = 0; i < TEST_COUNT; i++) {
            t[i] = new Thread(new RunSort());
            t[i].start();
        }
        for (int i = 0; i < TEST_COUNT; i++) {
            t[i].join();
        }
        long stop = System.currentTimeMillis();
        System.out.println(stop - start + "  -- sortThreads()");
    }

    static void sortPool() throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);
        for (int i = 0; i < TEST_COUNT; i++) {
            service.submit(new RunSort());
        }
        service.shutdown();
        service.awaitTermination(1000, TimeUnit.SECONDS);
        long stop = System.currentTimeMillis();
        System.out.println(stop - start + "  -- sortPool()");
    }

    static void sortPool2() throws InterruptedException {
        long start = System.currentTimeMillis();
        ThreadPool pool = new ThreadPool(POOL_SIZE);
        for (int i = 0; i < TEST_COUNT; i++) {
            pool.execute(new RunSort());
        }
        pool.shutDown(true);
        long stop = System.currentTimeMillis();
        System.out.println(stop - start + "  -- sortPool2()");
    }

    public static void main(String[] args) throws InterruptedException {
        SortTest.fill(org);

        //simpleTest();
        //sortTest();
        sortPool2();
        sortPool();
        sortThreads();

        System.out.println();
        sortPool2();
        sortPool();
        sortThreads();
    }
}