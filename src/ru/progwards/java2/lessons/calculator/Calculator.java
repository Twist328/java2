package ru.progwards.java2.lessons.calculator;


public class Calculator {


    public static void main(String[] args) {
        System.out.println("************************************");
        System.out.println("результат вычислений:  "+ calculate("8/3*2+8/2-5")); //6
        System.out.println("результат вычислений:  "+ calculate("1+3*2-9/3*9")); //0
        System.out.println("************************************");
    }

        /*Реализуйте метод public static int calculate(String expression), который вычисляет арифметическое выражение,
         заданное в виде символьной строки. Выражение содержит только целые цифры и знаки арифметических операций +-
         При вычислении должны учитываться приоритеты операций, например, результат вычисления выражения "2+3*2"
         должен быть равен 8. По оригинальному условию задачи все числа содержат не более одной цифры, пробелов в строке нет.*/

    public static int calculate(String expression) {
        String str = expression; // создание символьной строки
        int i = Integer.valueOf(str.substring(0, 1));
        if (str.length() == 1) return i;
        String s = str.substring(1, 2);

        while (s.equals("*") || s.equals("/")) {
            if (s.equals("*")) {

                i *= Integer.valueOf(str.substring(2, 3));

            } else if (s.equals("/"))
                i /= Integer.valueOf(str.substring(2, 3));
            str = str.substring(2);
            if (str.length() == 1) return i;
            s = str.substring(1, 2);
        }
        if (s.equals("+")) {
            i += calculate(str.substring(2));
        } else if (s.equals("-"))
            i -= calculate(str.substring(2));
        return i;

    }
}
