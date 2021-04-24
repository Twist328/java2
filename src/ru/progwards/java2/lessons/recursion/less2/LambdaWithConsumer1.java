package ru.progwards.java2.lessons.recursion.less2;

import ru.progwards.java2.lessons.recursion.less2.Book;

import java.util.function.Consumer;

public class LambdaWithConsumer1 {
    public static void main(String[] args) {
        Consumer doIt = x -> System.out.println(x);// может указываться и без Generic
        // аналогично вызову:
        // Consumer<Object> doIt = x -> System.out.println(x);
        doIt.accept(55555);
        doIt.accept("Строка");
        doIt.accept(new Book("Капитанская дочка", "Пушкин", 545));
    }
}

