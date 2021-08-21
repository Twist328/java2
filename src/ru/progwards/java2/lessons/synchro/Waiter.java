package ru.progwards.java2.lessons.synchro;

import java.util.TreeMap;

public class Waiter {
    private TreeMap<Integer, Fork> forks;

    public Waiter() {
        forks = new TreeMap<>();
        for (int i = 0; i < 5; i++)
            forks.put(i, new Fork(i));
    }

    public TreeMap<Integer, Fork> getForks() {
        return forks;
    }
}
