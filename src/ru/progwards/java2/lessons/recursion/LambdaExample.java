package ru.progwards.java2.lessons.recursion;

import java.util.List;

public class LambdaExample { // итерация с помощью ф.лямбда
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);

        list.forEach(element-> System.out.println("element="+element));
    }

}

