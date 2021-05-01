package ru.progwards.java2.lessons.less4;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Test9 {
    static final int MILLION = 1000000;
    static final int ITERATIONS = MILLION/10;
    static final int ITERATIONS2 = 100;
    static final int SHIFT = MILLION*100;
    static final int PRIORITY = 5;

    static class QElement {
        static int number;
        public Integer priority;
        public Integer num;

        QElement(int priority, int num) {
            this.priority = Integer.valueOf(priority*SHIFT+number++);
            this.num = num;
        }
    }

    public static final int randomInterval(int min, int max) {
        return (int)((Math.random() * (max - min)) + min);
    }

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        Comparator<QElement> comparator = new Comparator<>() {
            @Override
            public int compare(QElement o1, QElement o2) {
                return o2.priority.compareTo(o1.priority);
            }
        };
        PriorityQueue<QElement> q1 = new PriorityQueue<>(comparator);
        for(int i=0; i<ITERATIONS; i++) {
            for(int j=0; j<ITERATIONS2; j++)
                q1.offer(new QElement(randomInterval(0,PRIORITY-1), randomInterval(0, MILLION)));
            while(q1.size() > 0)
                q1.poll();
        }
        long t2 = System.currentTimeMillis();
        PQueue<Integer> q2 = new PQueue<>(PRIORITY);
        for(int i=0; i<ITERATIONS; i++) {
            for(int j=0; j<ITERATIONS2; j++)
                q2.offer(randomInterval(0,PRIORITY-1), randomInterval(0, MILLION));
            while(q2.size() > 0)
                q2.poll();
        }
        long t3 = System.currentTimeMillis();

        System.out.println("offer+poll q1 = "+(t2-t1));
        System.out.println("offer+poll q2  = "+(t3-t2));

    }
}

