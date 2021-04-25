package ru.progwards.java2.lessons.less2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StreamMaxLabmdaWithOptional {   //Optional<T> на примере max
    public static void main(String[] args) {
        List<Book> list = new ArrayList<>(List.of(
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        // преобразование строки в число через лямбду Function
        Optional<Book> oMax = list.stream().max(Comparator.comparingDouble(x -> x.price));
        Book max = oMax.get();
        // вывод на консоль
        System.out.println(max);
    }
}
