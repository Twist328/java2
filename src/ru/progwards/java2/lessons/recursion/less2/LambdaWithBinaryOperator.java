package ru.progwards.java2.lessons.recursion.less2;

import java.util.function.BinaryOperator;

public class LambdaWithBinaryOperator { //Функциональный интерфейс BinaryOperator<T>
    public static void main(String[] args) { //public interface BinaryOperator<T> extends BiFunction<T,T,T> {
        // единственный абстрактный метод apply наследуется
        BinaryOperator<Integer> mod = (x1, x2) -> x1 % x2;
        System.out.println(mod.apply(16, 5));
        System.out.println(mod.apply(5, 16));
    }
}

