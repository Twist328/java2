package ru.progwards.java2.lessons.recursion;

import java.util.ArrayList;
import java.util.List;

class Book {  // пример с сортировкой по цене
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
        List<Book> list = new ArrayList<>(List.of(
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
       list.sort((a,b)->Double.compare(a.price, b.price));
       list.forEach(System.out::println);
    }
}

