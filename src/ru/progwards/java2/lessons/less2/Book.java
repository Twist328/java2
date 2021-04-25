package ru.progwards.java2.lessons.less2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Book {
    String name;
    String author;
    double price;

    public Book(String name, String author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    @Override
    public String toString() {
        return author + ", " + name + ", " + price;
    }

    public static void main(String[] args) {
        List<Book> list = new ArrayList<>(List.of(            // пример с сортировкой по цене
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        list.sort((a, b) -> Double.compare(a.price, b.price));
        list.forEach(System.out::println);
        System.out.println("________________________________________________________________");
        List<Book> list1 = new ArrayList<>(List.of(               // пример с сортировкой по автору
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        list1.sort((a, b) -> a.author.compareTo(b.author));
        list1.forEach(System.out::println);
        System.out.println("_______________________________________________________________");
        List<Book> list2 = new ArrayList<>(List.of(                      // пример с сортировкой по названию
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        list2.sort(Comparator.comparing(a -> a.name));
        list2.forEach(System.out::println);
        System.out.println("_______________________________________________________________");
        List<Book> list3 = new ArrayList<>(List.of(      //Здесь добавляем эффективную финальную переменную msg, переменные которые изменялись использовать нельзя
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        list3.sort(Comparator.comparing(a -> a.name));
        String msg = "Книга: ";
        list3.forEach(element -> System.out.println(msg + element));

    }
}


