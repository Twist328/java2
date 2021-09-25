package ru.progwards.java2.lessons.less14.anothePhilosopher;

public class Philosopher implements Runnable { // Member variables, methods defined earlier

    private Object leftFork;
    private Object rightFork;

    private void doAction(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep(((int) (Math.random() * 100)));
    }

    public Philosopher(Object leftFork, Object rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }


    @Override
    public void run() {
        try {
            while (true) {
                //thinking
                try {
                    doAction(System.nanoTime() + ": Thinking");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (leftFork) {
                    try {
                        doAction(System.nanoTime() + ": Picked up left fork");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (rightFork) { //
                        // eating
                        try {
                            doAction(System.nanoTime() + ": Picked up right fork - eating");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            doAction(System.nanoTime() + ": Put down right fork");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }
        } finally {

        }
    }
}