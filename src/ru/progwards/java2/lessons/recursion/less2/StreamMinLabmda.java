package ru.progwards.java2.lessons.recursion.less2;

import ru.progwards.java2.lessons.recursion.less2.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StreamMinLabmda {  //Поиск min и max элементов через stream
    public static void main(String[] args) {
        List<Book> list = new ArrayList<>(List.of(
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        // преобразование строки в число через лямбду Function
        Book min = list.stream().min((x,y) -> Double.compare(x.price, y.price)).get();
        Book max = list.stream().max(Comparator.comparingDouble(x -> x.price)).get();
        // вывод на консоль
        System.out.println(min);
        System.out.println(max);
    }
}

