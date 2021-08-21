package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Simposion {
    private long reflectTime;
    private long eatTime;
    private int quantityPhilosopher;

    public Simposion(long reflectTime, long eatTime) {
       this.reflectTime = reflectTime;
        this.eatTime = eatTime;
   }

    public Simposion() {
        quantityPhilosopher = 5;
    }

    void start() {
        Waiter waiter = new Waiter();

        Philosopher[] philosophers = new Philosopher[quantityPhilosopher];
        Fork[] forks = new Fork[quantityPhilosopher];

        for (int i = 0; i < quantityPhilosopher; i++) {
            forks[i] = new Fork(i);
        }

        for (int i = 0; i < quantityPhilosopher; i++) {
            Fork left = waiter.getForks().get(i);
            Fork right = waiter.getForks().get((i+1)%quantityPhilosopher);

            philosophers[i] = new Philosopher("Philosopher" + (i+1), left, right, 200, 300, waiter);

            Thread t = new Thread(philosophers[i]);
            t.start();
        }
    }

    public static void main(String[] args) {
        new Simposion().start();



    }
}