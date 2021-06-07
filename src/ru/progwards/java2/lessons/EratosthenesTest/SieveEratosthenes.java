package ru.progwards.java2.lessons.EratosthenesTest;

import java.util.ArrayList;
import java.util.List;

public class SieveEratosthenes {
    private boolean[] nums;

    public static void main(String[] args) {
        int n = 100;
        SieveEratosthenes s = new SieveEratosthenes(n);
        for (int i = 0; i < s.nums.length; i++) {
            if (s.nums[i]) {
                System.out.println(i);
            }
        }
    }

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
}