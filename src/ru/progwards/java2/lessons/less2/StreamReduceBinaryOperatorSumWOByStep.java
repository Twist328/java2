package ru.progwards.java2.lessons.less2;

import java.util.List;

public class StreamReduceBinaryOperatorSumWOByStep {  //Сумма через reduce без identity по шагам
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        // сумма через reduce и лямбду BinaryOperator с Optional
        int sum = list.stream().
                reduce(
                        (a, x)-> {
                            System.out.println("a = " + a + ", x = " + x);
                            return a + x;
                        }
                ).orElse(0);
        // вывод на консоль
        System.out.println("Сумма всех элементов: " + sum);
    }
}

