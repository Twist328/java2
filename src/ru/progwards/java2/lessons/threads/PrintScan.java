package ru.progwards.java2.lessons.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintScan {
    private final static Lock lock1 = new ReentrantLock();
    private final static Lock lock2 = new ReentrantLock();

    static void print(String name, int pages) {
        lock1.lock();
        try {
            for (int i = 1; i <= pages; i++) {
                System.out.println("print " + name + " page " + i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock1.unlock();
        }
    }

    static void scan(String name, int pages) {
        lock2.lock();
        System.out.println("************************");
        try {
            for (int i = 1; i <= pages; i++) {
                System.out.println("scan " + name + " page " + i);
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        } finally {
            lock2.unlock();

        }
    }



    public static void main(String[] args) {
        System.out.println("\n************************");
        Thread t1 = new Thread(new RunnablePrint("doc1", 20));
        Thread t2 = new Thread(new RunnablePrint("doc2", 15));
        Thread t3 = new Thread(new RunnablePrint("doc3", 10));
        t1.start();
        t2.start();
        t3.start();
    }
}

