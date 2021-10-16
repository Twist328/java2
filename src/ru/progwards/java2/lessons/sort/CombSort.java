package ru.progwards.java2.lessons.sort;

import java.util.Arrays;

public class CombSort {
    public static <E extends Comparable<? super E>> void sort(E[] a) {
        int gap = a.length;
        boolean swapped = true;
        while (gap > 1 || swapped) {
            if (gap > 1)
                gap = (int) (gap / 1.247330950103979);

            int i = 0;
            swapped = false;
            while (i + gap < a.length) {
                if (a[i].compareTo(a[i + gap]) > 0) {
                    E t = a[i];
                    a[i] = a[i + gap];
                    a[i + gap] = t;
                    swapped = true;
                }
                i++;
            }
        }
    }

    public static void main(String[] args) {
        int[]a=new int[]{1000,999,888,777,897,678,99,55,44,88,99,33,33,66,44,11,-33,-77,-66,777,333,111,222,-222,-999,-10000,-555,-444};
        long start=System.nanoTime();
        Arrays.sort(a);
        long sorted=System.nanoTime()-start;
        System.out.println(Arrays.toString(a)+"Execution time: " + sorted);
    }
}
