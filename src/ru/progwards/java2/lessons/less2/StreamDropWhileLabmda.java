package ru.progwards.java2.lessons.less2;

import java.util.List;
import java.util.stream.Collectors;

public class StreamDropWhileLabmda { //сортировка  по критерию через поток с помощью stream.dropWhile
    public static void main(String[] args) {
        List<Integer> list = List.of(5, 144, 2, 1, 233, 8, 13, 21, 34, 55, 3, 1, 89);
        // формирование списка по критерию через поток
        List<Integer> filtered =
                list.stream().sorted().dropWhile(x ->/*Функциональный интерфейс Predicate*/ x < 100).
                        collect(Collectors.toList());
        // вывод на консоль
        System.out.println(filtered);
    }
}

