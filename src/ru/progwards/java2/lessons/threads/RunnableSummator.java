package ru.progwards.java2.lessons.threads;

import java.math.BigInteger;
import java.util.List;

public class RunnableSummator implements Runnable {
    private BigInteger start;
    private BigInteger finish;
    private List<BigInteger> list;

    RunnableSummator(BigInteger start, BigInteger finish, List<BigInteger> list) {
        this.start = start;
        this.finish = finish;
        this.list = list;

    }
    // метод суммирует числа от start до finish
    public BigInteger sumBlock(BigInteger start, BigInteger finish) {
        BigInteger sum = BigInteger.ZERO;

        for (BigInteger i = start; i.compareTo(finish) <= 0; i = i.add(BigInteger.ONE)) {
            sum = sum.add(i);
        }

        return sum;
    }
    @Override
    public void run() {
        list.add(sumBlock(start, finish));
    }


}
