package ru.progwards.java2.lessons.recursion.less2;

import java.util.function.Supplier;

public class LambdaWithSupplier {  //T get();  единственный абстрактный метод
    public static void main(String[] args) {
        Supplier<Integer> randomFactory =
                () -> (int) (Math.random() * 10 + 1);
        System.out.println("Случайные числа от 1 до 10:");
        for (int i = 0; i < 5; i++)
            System.out.println(randomFactory.get());
    }
}

