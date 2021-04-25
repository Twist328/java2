package ru.progwards.java2.lessons.less2;

import java.util.List;

public class StreamReduceBinaryOperatorMax1 { //Max через reduce с identity по шагам
    public static void main(String[] args) {
        List<Integer> list = List.of(5, 2, 1, 8, 13, 21, 34, 55, 3, 1);
        // max через reduce и лямбду BinaryOperator
        int max = list.stream().reduce(0,
                (a, x)-> {
                    System.out.println("a=" + a + ", x=" + x);
                    return a < x ? x : a;
                }
        );
        // вывод на консоль
        System.out.println("max = " + max);
    }
}

