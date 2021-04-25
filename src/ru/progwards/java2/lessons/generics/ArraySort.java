package ru.progwards.java2.lessons.generics;

import java.util.Arrays;

public class ArraySort {

    public static void main(String[] args) {
        Double[] array = {2.1, 8.2, 0.2, 3.14};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static <T extends Comparable> void sort(T... array) {

        for (int i = array.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j].compareTo(array[i]) > 0) {
                    T tmpValue = array[i];
                    array[i] = array[j];
                    array[j] = tmpValue;
                }
            }
        }
    }
}
