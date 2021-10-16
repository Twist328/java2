package ru.progwards.java2.lessons.testsjava1;

import ru.progwards.java2.lessons.sort.InsertionSort;

import java.util.Arrays;

public class Sorting {
    public static void insertionSort(String array[]) {
        for (int j = 1; j < array.length; j++) {
            String current = array[j];
            int i = j-1;
            while ((i > -1) && (array[i].compareTo(current)>0)) {
                array[i+1] = array[i];
                i--;
            }
            array[i+1] = current;
        }
    }
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
    public static void insertionSort0(int a[]) {
        for (int j = 1; j < a.length; j++) {
            int cur = a[j];
            int i = j - 1;
            while (i >= 0 && (a[i] > cur)) {
                //a[i + 1] = a[i];
                i--;
            }
            if (i + 2 < a.length && j - i - 2 >= 0) {
                System.arraycopy(a, i + 1, a, i + 2, j - i - 1); // затирает всё одним значением//исправлено для интов, Стрингу не любит
            }
            a[i + 1] = cur;
        }
    }
        public static void insertionSort2(double a[]) {
            for (int j = 1; j < a.length; j++) {
                double cur = a[j];
                int i = j - 1;
                while (i >-1 && (a[i]>cur) ) {
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
        System.out.println("\n**********************************************");
        int[]a=new int[]{1000,999,888,777,897,678,99,55,44,88,99,33,33,66,44,11,-33,-77,-66,777,333,111,222,-222,-999,-10000,-555,-444};
        long start=System.nanoTime();
        insertionSort(a);
        long sorted=System.nanoTime()-start;
        System.out.println(Arrays.toString(a)+"Execution time: " + sorted);
        System.out.println("**********************************************");
    }
}
