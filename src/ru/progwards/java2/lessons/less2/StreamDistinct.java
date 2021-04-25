package ru.progwards.java2.lessons.less2;

import java.util.List;
import java.util.stream.Collectors;

public class StreamDistinct {  //stream.distinct, итерация  через поток с фильтром,с удалением дубликатов чисел
    public static void main(String[] args) {
        List<Integer> list = List.of(5, 144, 2, 1, 233, 8, 13, 21,144, 34, 55, 3, 1, 89);
        // формирование списка по критерию через поток
        List<Integer> filtered =
                list.stream().distinct().collect(Collectors.toList());
        // вывод на консоль
        System.out.println(filtered);
    }
}
