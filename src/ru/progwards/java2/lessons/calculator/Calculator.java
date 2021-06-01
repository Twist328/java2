package ru.progwards.java2.lessons.calculator;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    //Реализуйте метод public static int calculate(String expression), который вычисляет арифметическое выражение,
    // заданное в виде символьной строки. Выражение содержит только целые цифры и знаки арифметических операций +-*/
    //При вычислении должны учитываться приоритеты операций, например, результат вычисления выражения "2+3*2"
    //должен быть равен 8. По оригинальному условию задачи все числа содержат не более одной цифры, пробелов в строке нет.

    public static int calculate(String expression) {
        String str = expression;
        int i = Integer.valueOf(str.substring(0, 1));
        if (str.length() == 1) return i;
        String op = str.substring(1, 2);
        while (op.equals("*") || op.equals("/")) {
            if (op.equals("*"))
                i *= Integer.valueOf(str.substring(2, 3));
            else if (op.equals("/"))
                i /= Integer.valueOf(str.substring(2, 3));
            str = str.substring(2);
            if (str.length() == 1) return i;
            op = str.substring(1, 2);
        }
        if (op.equals("+"))
            i += calculate(str.substring(2));
        else if (op.equals("-"))
            i -= calculate(str.substring(2));
        return i;
    }

    public static void main(String[] args) {
        System.out.println(calculate("2+3*2+7*2")); //22
        System.out.println(calculate("1+1*2-5/5")); //18
        // System.out.println(calculate1("1+2*2+(-5/5)+1"));//11+4-2+11=24
        //System.out.println(calculate1("-11+1-(02*2)-(-10/(002-1+4)+11)"));//-23*/
    }
}
