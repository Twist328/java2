package ru.progwards.java2.lessons.testsjava1;

import java.util.Arrays;

public class Sorting {
    public static void insertionSort(int array[]) {
        for (int j = 1; j < array.length; j++) {
            int current = array[j];
            int i = j-1;
            while ((i > -1) && (array[i] > current)) {
                array[i+1] = array[i];
                i--;
            }
            array[i+1] = current;
        }
    }
    public static void insertionSort1(int a[]) {
    for (int j = 1; j < a.length; j++) {
        int cur = a[j];
        int i = j - 1;
        while (i >=0 && (a[i]>cur) ) {
            //a[i + 1] = a[i];
            i--;
        }
        if (i + 2 < a.length && j - i - 2 >= 0) {
            System.arraycopy(a, i+1, a, i+2, j -i -1); // нельзя использовать, затирает всё одним значением//исправлено для интов, Стрингу не любит
        }
        a[i + 1] = cur;
    }
}

    public static void main(String[] args) {
        int[] array = new int[]{1, 7, 5, 6, 9, 4, 2, 3};
        insertionSort(array);
        System.out.println(Arrays.toString(array));
        int[] array1 = new int[]{88,- 79, 54, 65, 9, 4, 2,0,-99,292,77, 3};
        insertionSort1(array1);
        System.out.println(Arrays.toString(array1));
    }
}
