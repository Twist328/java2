package ru.progwards.java2.lessons.recursion.less2;

import java.util.List;

class LoopExample {  //итерация без лямбда
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        for (Integer element : list) {
            System.out.println("element = " + element);
        }
    }
}
