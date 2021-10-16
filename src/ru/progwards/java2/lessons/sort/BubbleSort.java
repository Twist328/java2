package ru.progwards.java2.lessons.sort;

import java.util.Arrays;

public class BubbleSort {


    public static void bubbleSort(int[]a1) {
        for (int i=0; i<a1.length; i++) {
            for (int j = 0; j < a1.length - i - 1; j++) {
                int n = j+1;
                if (a1[j]>(a1[n]) ) {
                   int tmp = a1[j];
                    a1[j] = a1[n];
                    a1[j] = tmp;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                int n = j + 1;
                if (a[j].compareTo(a[n]) < 0) {
                    T tmp = a[j];
                    a[j] = a[n];
                    a[j] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[]a=new int[]{1000,999,888,777,897,678,99,55,44,88,99,33,33,66,44,11,-33,-77,-66,777,333,111,222,-222,-999,-10000,-555,-444};
        long start=System.nanoTime();
        bubbleSort(a);
        long sorted=System.nanoTime()-start;
        System.out.println(Arrays.toString(a)+"Execution time: " + sorted);
    }

}

