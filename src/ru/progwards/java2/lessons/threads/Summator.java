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
        return sumRes(list);
    }

    // метод позволяет потоку main дождаться выполнения потока thread
    private void threadJoin(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // метод складывает значения элементов списка
    private BigInteger sumRes(List<BigInteger> list) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < list.size(); i++) {
            result = result.add(list.get(i));
        }
        //System.out.println(result);
        return result;
    }
        public static void main(String[] args) {
            int num = 5555555;
            long l = 0;
            for (int i = 0; i <= num; i++) {
                l = l + i;
            }
            System.out.println("\n*****************************************");
            System.out.println("long   = " + l);

            long time = System.currentTimeMillis();
            int tr = 100_000;
            Summator test = new Summator(tr);
            System.out.println("result = " + test.sum(BigInteger.valueOf(num)) + "   потоков - " + tr);
            System.out.println("Время работы: " + (System.currentTimeMillis() - time) + " мс.");

            time = System.currentTimeMillis();
            tr = 2;
            test = new Summator(tr);
            //System.out.println("\n*****************************************");
            System.out.println("result = " + test.sum(BigInteger.valueOf(num)) + "   потоков - " + tr);
            System.out.println("Время работы: " + (System.currentTimeMillis() - time) + " мс.");
            System.out.println("*****************************************");
        }

    }

