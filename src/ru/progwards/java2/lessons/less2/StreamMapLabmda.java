package ru.progwards.java2.lessons.less2;

import java.util.List;
import java.util.stream.Collectors;

public class StreamMapLabmda { //
    public static void main(String[] args) {
        List<String> list =
                List.of("1", "1", "2", "3", "5", "8", "13", "21", "34", "55", "89");
        // преобразование строки в число через map (лямбда типа Function)
        List<Integer> filtered =
                list.stream().map(Integer::valueOf).collect(Collectors.toList());
        // вывод на консоль
        System.out.println(filtered);
    }
}

