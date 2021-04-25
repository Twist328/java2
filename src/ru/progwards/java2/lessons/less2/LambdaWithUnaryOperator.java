package ru.progwards.java2.lessons.less2;

import java.util.function.UnaryOperator;

public class LambdaWithUnaryOperator {   // единственный абстрактный метод apply наследуется от  Function
    public static void main(String[] args) {
        UnaryOperator<Integer> square = x -> x * x;
        System.out.println(square.apply(5));
        System.out.println(square.apply(-5));
    }
}

