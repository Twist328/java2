package ru.progwards.java2.lessons.recursion.less2;

import java.util.List;

public class StreamReduceBinaryOperatorSumWO {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        // сумма через reduce и лямбду BinaryOperator с Optional
        int sum = list.stream().reduce((a, x)-> a + x).orElse(0);
        // вывод на консоль
        System.out.println("Сумма всех элементов: " + sum);
    }
}

