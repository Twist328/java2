package ru.progwards.java2.lessons.less2;

import java.util.List;
import java.util.TreeSet;

public class TreeSetWithCustomComparator {
    public static void main(String[] args) {
        /*TreeSet<Integer> treeSet = new TreeSet<>(new Comparator<Integer>() {  //анонимный класс для сортировки компаратором
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(Math.abs(o1), Math.abs(o2));*/
        TreeSet<Integer> treeSet = new TreeSet<>((o1, o2) -> Integer.compare(Math.abs(o1), Math.abs(o2)));
            // лямбда вместо анонимного класса

        treeSet.addAll(List.of(3, 5, -1, -3, -5, -4, 4, -5, 5, 5, 1, 2, -2));
        System.out.println(treeSet);
    }

}
