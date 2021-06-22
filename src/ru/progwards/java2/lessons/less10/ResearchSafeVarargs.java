package ru.progwards.java2.lessons.less10;

import java.util.Arrays;
import java.util.Date;

public class ResearchSafeVarargs {
    public static <T> T[] unsafe(T... elements) {
        // Небезопасно: не всегда вернёт массив нужного типа
        return elements;
    }

    public static <T> T[] wrongUse(T elem) {
        // Ошибка, вместо массива T получим массив Object и... ClassCastException
        T[] array = unsafe(elem, elem, elem, elem, elem);
        return array;
    }

    public static void main(String[] args) {
//        String[] strings1 = unsafe("строка1", "строка2", "строка3");
//        System.out.println(Arrays.toString(strings1));
//        String[] strings2 = wrongUse("строка"); // ClassCastException
    }
}
