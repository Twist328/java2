package ru.progwards.java2.lessons.recursion.less2;

import java.util.function.Function;

public class LambdaWithFunction {  // ф. интерфейс Function (R apply(T t)) единственный абстрактный метод
    ////… другие default и static методы
    public static void main(String[] args) {
        Function<Double, Double> square = x -> x * x;
        System.out.println(square.apply(5.0));
        Function<Double, Double> squareRoot = x -> Math.sqrt(x);
        System.out.println(squareRoot.apply(25.0));
        Function<Double, Double> mod = x -> squareRoot.apply(square.apply(x));
        System.out.println(mod.apply(-5.0));
    }
}
