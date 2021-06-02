package ru.progwards.java2.lessons.calculator;


//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

public class Calculator {
    private int     pos;        // в каком месте строки разбираем
    private int     len;

    public Calculator() {

        /*Реализуйте метод public static int calculate(String expression), который вычисляет арифметическое выражение,
         заданное в виде символьной строки. Выражение содержит только целые цифры и знаки арифметических операций +-
         При вычислении должны учитываться приоритеты операций, например, результат вычисления выражения "2+3*2"
         должен быть равен 8. По оригинальному условию задачи все числа содержат не более одной цифры, пробелов в строке нет.*/
    }
    public static int calculate(String expression) throws  NumberFormatException{
        String str = expression;
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

    public static void main(String[] args) {
        System.out.println(calculate("3/3*2+8/2")); //6
        System.out.println(calculate("1*1*2-9/3-9")); //8
        //System.out.println(calculate("(4-2)/1+(1*9)"));

    }
}
