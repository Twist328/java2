package ru.progwards.java2.lessons.less2;

import java.util.function.Predicate;

public class LambdaWithPredicate {  //пример с функциональным интерфейсом Predicate
    //boolean test(T t) единственный абстрактный метод//другие default и static методы
    public static void main(String[] args) {
        Predicate<Integer> isEven = x -> x % 2 == 0;// функция для определения четности/нечетности
        System.out.println(isEven.test(1));
        System.out.println(isEven.test(1000));
    }
}

