package ru.progwards.java2.lessons.less3;

import java.util.ArrayList;
import java.util.List;

public class TestDjen {
    // Создайте статический метод с именем from, который принимает параметром массив,
    // обобщающего типа, создает новый ArrayList, копирует в него содержимое массива и возвращает ArrayList в качестве результата метода.
    public static void main(String[] args) {
        TestDjen testDjen=new TestDjen();
        String[] as = {"Red", "Blue", "Green"};
        System.out.println("------------------------------");
        System.out.println(from(as));
        System.out.println("______________________________");
        Integer[] ai = {1,2,3,4,5};
        List<Integer> li = from(ai);
        swap(li,1,3);
        System.out.println(li);
        System.out.println("_______________________________");
        System.out.println(compare("Петя", "Вася"));
        System.out.println(compare(CompareResult.GREATER, CompareResult.GREATER));
        System.out.println(compare(1,2));
    }
    static <T> ArrayList<T> from(T... arrays) {
        ArrayList<T> list = new ArrayList<T>(arrays.length);
        for (T i: arrays) {
            list.add(i);
        }
        return list;
    }

    // Создайте статический метод с именем swap типа  void, который принимает параметром List, обобщающего типа, и два int как индексы в списке.
    // Реализовать метод, который меняет элементы с указанными индексами местами.

    static <T> void swap(List<T> list, int i1, int i2) {
        T res = list.get(i1);
        list.set(i1, list.get(i2));
        list.set(i2, res);
    }

    // Определен enum CompareResult {LESS, EQUAL, GREATER};
    //
    //Создайте статический метод с именем compare, который содержит 2 параметра обобщающего типа, и сравнивает их через метод compareTo. Метод compare должен возвращать CompareResult, причем
    //
    //CompareResult.LESS если первый параметр меньше второго
    //CompareResult.GREATER если первый параметр больше второго
    //CompareResult.EQUAL если первый параметр равен второму

    enum CompareResult {LESS, EQUAL, GREATER};
    static <T extends Comparable> CompareResult compare(T o1, T o2) {
        int res = o1.compareTo(o2);
        if(res==0) return CompareResult.EQUAL;
        else if(res>0) return CompareResult.GREATER;
        else return CompareResult.LESS;
    }

}

