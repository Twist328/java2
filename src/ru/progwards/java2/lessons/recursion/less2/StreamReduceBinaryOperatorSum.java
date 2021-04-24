package ru.progwards.java2.lessons.recursion.less2;

import java.util.List;

public class StreamReduceBinaryOperatorSum { //Сумма через reduce
    public static void main(String[] args) {
        List<Integer> list = List.of(10, 15, 3, 4, 5);
        // подсчёт суммы через reduce и лямбду BinaryOperator
        int sum = list.stream().reduce(0, (a, x)-> a + x);
        // вывод на консоль
        System.out.println("Сумма всех элементов: " + sum);
    }
}
