package ru.progwards.java2.lessons.recursion;

import java.util.function.Consumer;

public class LambdaWithConsumer {
    public static void main(String[] args) {
        Consumer<Integer> doIt = x -> System.out.println(x); //пример с функциональным интерфейсом Consumer,
        // используется в forEach// void accept(T t) единственный абстрактный метод
         //… другие default и static методы
        doIt.accept(55555);
    }
}

