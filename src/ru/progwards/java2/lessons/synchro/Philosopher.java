package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher implements Runnable {
    private String name;
    private Fork right;
    private Fork left;
    private long reflectTime;
    private long eatTime;
    private long reflectSum;
    private long eatSum;
    private static Semaphore semaphore = new Semaphore(3);
    private Waiter waiter;
    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();


    public Philosopher(String name, Fork left, Fork right, long reflectTime, long eatTime, Waiter waiter) {
        this.name = name;
        this.right = right;
        this.left = left;
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
        semaphore = new Semaphore(3);
        this.waiter = waiter;
    }

    public Fork getRight() {
        return right;
    }

    public Fork getLeft() {
        return left;
    }

    public void setRight(Fork right) {
        this.right = right;
    }

    public void setLeft(Fork left) {
        this.left = left;
    }

    public void reflect() {
        System.out.println(name + " размышляет");
        try {
            Thread.sleep(reflectTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAction(String action) {
        System.out.println(name + " " + action);
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void eat() {
        System.out.println(name + " ест");
        try {
            Thread.sleep(eatTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void putUpLeftFork(Waiter waiter) {
        lock1.lock();
        if (waiter.getForks().get(left.getNumber()).isFreeStatus()) {
            System.out.println(name + " взял левую вилку");
            left.setFreeStatus(false);
            waiter.getForks().put(left.getNumber(), left);
        }
        lock1.unlock();
    }

    public void putUpRightFork(Waiter waiter) {
        lock2.lock();
        if (waiter.getForks().get(right.getNumber()).isFreeStatus()) {
            System.out.println(name + " взял правую вилку");
            right.setFreeStatus(false);
            waiter.getForks().put(right.getNumber(), right);
        }
        lock2.unlock();
    }

    public void putUpFork(Waiter waiter) {
        if (waiter.getForks().get(left.getNumber()).isFreeStatus()) {
            left.setFreeStatus(false);
            waiter.getForks().put(left.getNumber(), left);
            if (waiter.getForks().get(right.getNumber()).isFreeStatus()) {
                right.setFreeStatus(false);
                waiter.getForks().put(right.getNumber(), right);
                eat();
            }

        }
    }

    public void putDownRightFork(Waiter waiter) {
        right.setFreeStatus(true);
        System.out.println(name + " положил правую вилку");
        waiter.getForks().put(right.getNumber(), right);
    }

    public void putDownLeftFork(Waiter waiter) {
        left.setFreeStatus(true);
        System.out.println(name + " положил левую вилку");
        waiter.getForks().put(left.getNumber(), left);
    }


    public  void putDownFork(Waiter waiter) {
        right.setFreeStatus(true);
        waiter.getForks().put(right.getNumber(), right);
        left.setFreeStatus(true);
        waiter.getForks().put(left.getNumber(), left);
        reflect();
    }

    @Override
    public void run() {
        while (true) {
            doAction("thinking");
            synchronized (left) {
                doAction("put up left fork");
                synchronized (right) {
                    doAction("put up right fork - eating");
                    doAction("put down right fork");
                }
                doAction("put down left fork. Back to thinking!");
            }
            reflect();
            putUpLeftFork(waiter);
            if (!left.isFreeStatus())
                putUpRightFork(waiter);
            if (!right.isFreeStatus() && !left.isFreeStatus()) {
                eat();
                putDownRightFork(waiter);
                putDownLeftFork(waiter);
            }
        }
    }
}
