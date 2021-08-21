package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.Semaphore;

public class Fork {
    private boolean freeStatus;
    private int number;

    public Fork(int number) {
        this.number = number;
        freeStatus = true;
    }

    public boolean isFreeStatus() {
        return freeStatus;
    }

    public void setFreeStatus(boolean freeStatus) {
        this.freeStatus = freeStatus;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
