package ru.progwards.java2.lessons.threads;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Summator {
    int count;

    Summator(int count) {
        this.count = count;
    }

    public BigInteger sum(BigInteger number) {
        if (count == 0)
            return null;

        // в списке содержатся результаты выполнения потоками метода sumBlock
        List<BigInteger> list = new ArrayList<>();

        // sizeBlock - значение размера блока, который получается при делении number на count
        BigInteger sizeBlock = number.divide(new BigInteger(Integer.toString(count)));
        // каждый поток суммирует числа от start до finish
        BigInteger start = BigInteger.ONE;
        BigInteger finish = sizeBlock;
        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                Thread thread = new Thread(new RunnableSummator(start, number, list));
                thread.start();
                threadJoin(thread);
            } else {
                Thread thread = new Thread(new RunnableSummator(start, finish, list));
                thread.start();
                threadJoin(thread);
            }
            // меняем значения для следующего потока
            start = finish.add(BigInteger.ONE);
            finish = finish.add(sizeBlock);
        }
        //System.out.println(list);
        return resultSum(list);
    }

    // метод позволяет потоку main дождаться выполнения потока thread
    private void threadJoin(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // метод суммирует числа от start до finish
    private BigInteger sumBlock(BigInteger start, BigInteger finish) {
        BigInteger sum = BigInteger.ZERO;

        for (BigInteger i = start; i.compareTo(finish) <= 0; i = i.add(BigInteger.ONE)) {
            sum = sum.add(i);
        }
       // System.out.println(sum);
        return sum;

    }

    // метод складывает значения элементов списка
    private BigInteger resultSum(List<BigInteger> list) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < list.size(); i++) {
            result = result.add(list.get(i));
        }
        //System.out.println(result);
        return result;
    }

    class RunnableSummator implements Runnable {
        private BigInteger start;
        private BigInteger finish;
        private List<BigInteger> list;

        RunnableSummator(BigInteger start, BigInteger finish, List<BigInteger> list) {
            this.start = start;
            this.finish = finish;
            this.list = list;
            //System.out.println(list);
        }

        @Override
        public void run() {
            list.add(sumBlock(start, finish));
        }
    }

    public static void main(String[] args) {

        Summator s = new Summator(10);

        System.out.println("\n**********************************");
        System.out.println(s.sum(new BigInteger("5")));
        System.out.println("**********************************");
    }
}
