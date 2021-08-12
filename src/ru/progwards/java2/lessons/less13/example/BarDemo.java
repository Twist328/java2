package ru.progwards.java2.lessons.less13.example;

import java.util.concurrent.*;

class BarDemo {
    public static void main(String args[]) {
       /*CyclicBarrier cb = new CyclicBarrier(3, new BarAction());

        System.out.println("Запуск потоков");

        new MyThread1(cb, "A");
        new MyThread1(cb, "B");
        new MyThread1(cb, "C");*/

            CyclicBarrier cb = new CyclicBarrier(2, new BarAction());

            System.out.println("Запуск потоков");

            new MyThread1(cb, "A");
            new MyThread1(cb, "B");
            new MyThread1(cb, "C");
            new MyThread1(cb, "X");
            new MyThread1(cb, "Y");
            new MyThread1(cb, "Z");
        }
    }
// Поток исполнения, использующий барьер типа CyclicBarrier

class MyThread1 implements Runnable {
    CyclicBarrier cbar;
    String name;

    MyThread1(CyclicBarrier c, String n) {
        cbar = c;
        name = n;
        new Thread(this).start();
    }

    public void run() {
        System.out.println(name);


        try {
            cbar.await();
        } catch (BrokenBarrierException exc) {
            System.out.println(exc);
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
    }
}

// Объект этого класса вызывается по достижении
// барьера типа CyclicBarrier

class BarAction implements Runnable {
    public void run() {
        System.out.println("Барьер достигнут");
    }
}
