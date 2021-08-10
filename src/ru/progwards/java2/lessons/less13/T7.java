package ru.progwards.java2.lessons.less13;

import java.util.concurrent.CountDownLatch;

public class T7 {
    final static int THREADS_COUNT = 3;

    static class MyThread extends Thread {
        CountDownLatch count;

        public MyThread(CountDownLatch count) {
            this.count = count;
        }

        @Override
        public void run() {
            count.countDown();
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("thread started "+count.getCount());

        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(THREADS_COUNT);

        for (int i = 0; i < THREADS_COUNT; i++) {
            new MyThread(count).start();
        }

        count.await();
    }
}

