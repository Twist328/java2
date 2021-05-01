package ru.progwards.java2.lessons.less4;

import java.util.ArrayDeque;

public class PQueue <E>{
    private ArrayDeque<E>[] queue;

    public PQueue(int priority) {
        queue = new ArrayDeque[priority];
        for(int i=0; i<priority; i++)
            queue[i] = new ArrayDeque<>();
    }

    public void offer(int priority, E element) {
        queue[priority].add(element);
    }

    public E poll() {
        for(int i=0; i<queue.length; i++)
            if (queue[i].size() > 0)
                return queue[i].poll();

        return null;
    }

    public int size() {
        int size = 0;
        for(int i=0; i<queue.length; i++)
            size += queue[i].size();
        return size;
    }
}

