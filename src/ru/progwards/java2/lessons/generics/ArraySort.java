package ru.progwards.java2.lessons.generics;

import java.util.Arrays;

public class ArraySort {

    public static void main(String[] args) {
        Double[] array = {2.1, 8.2, 0.2, 3.14};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static <T extends Comparable> void sort(T... item) {

        for (int i = item.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (item[j].compareTo(item[i]) > 0) {
                    T tmpValue = item[i];
                    item[i] = item[j];
                    item[j] = tmpValue;
                }
            }
        }
    }
}
