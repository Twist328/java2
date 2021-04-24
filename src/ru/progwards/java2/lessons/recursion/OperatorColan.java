package ru.progwards.java2.lessons.recursion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class OperatorColan { //colan это :: обозначение оператора
    static boolean isEven(Integer i1) {
        return i1 % 2 == 0;
    }
    public static void main(String[] args) {
        Predicate<Integer> predicate = OperatorColan::isEven;
        // аналогично лямбда-выражению:
        // Predicate<Integer> predicate = x -> isEven(x);
        System.out.println(predicate.test(1000));
        Consumer<Book> p = System.out::println;
        p.accept(new Book("Капитанская дочка", "Пушкин", 545));
        System.out.println("______________________________________________________________");
        List<Book> list = new ArrayList<>(List.of(
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        list.sort(Comparator.comparing(a -> a.name));
        //list.forEach(element -> System.out.println(element));
        list.forEach(System.out::println);
    }
}

