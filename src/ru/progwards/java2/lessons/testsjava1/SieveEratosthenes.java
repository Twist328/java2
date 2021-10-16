package ru.progwards.java2.lessons.testsjava1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class SieveEratosthenes {// программа вычисляет простые натуральные числа Эратосфена (Сито Эратосфена)
    //делящиеся на 1 и на само себя без остатка, остальные числа(не простые )могут делиться и на другие числа : 2,3,5 ...

    private boolean[] nums;

    /**
     * @param max
     */
    public SieveEratosthenes(int max) {
        sieve(max);
    }

    private boolean[] sieve(int max) {
        nums = new boolean[max + 1];
        initFlags();
        for (int i = 2; i * i < max; i++) {
            for (int j = i * i; j <= max; j += i) {//cross off non-primes
                nums[j] = false;
            }
        }
        return nums;
    }

    private void initFlags() {
        if (nums != null && nums.length > 1) {
            nums[0] = false;
            nums[1] = false;
            nums[2] = true;
        }
        for (int i = 3; i < nums.length; i++) {
            nums[i] = true;
        }
    }

    public List<Long> sieveToList() {
        List<Long> sieveList = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]) {
                sieveList.add((long) i);
            }
        }
        return sieveList;
    }

    public static void main(String[] args) {
        System.out.println("\n*****************************************************************************************");
        //int n = 150;
        SieveEratosthenes s = new SieveEratosthenes(100);

        System.out.println("Простые натуральные числа Эратосфена: " + s.sieveToList());
        System.out.println("*****************************************************************************************");
    }
}