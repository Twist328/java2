package ru.progwards.java2.lessons.less2;

import java.util.List;
import java.util.Optional;

public class StreamReduceBinaryOperatorSumWithOptional { //Сумма через reduce без identity (с Optional)
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        // сумма через reduce и лямбду BinaryOperator с Optional
        Optional<Integer> oSum = list.stream().reduce((a, x)->/* BinaryOperator*/a + x);
        int sum = oSum.orElse(0);
        // вывод на консоль
        System.out.println("Сумма всех элементов: " + sum);
    }
}

