package ru.progwards.java2.lessons.calculator;


public class Calculator {

    public static void main(String[] args) {
        System.out.println("\n************************************");
        System.out.println("результат вычислений:  "+ calculate("8/3*2+8/2-5")); //3
        System.out.println("результат вычислений:  "+ calculate("1+3*2-9/3*9")); //-20
        System.out.println("************************************");
    }

        /*Реализуйте метод public static int calculate(String expression), который вычисляет арифметическое выражение,
         заданное в виде символьной строки. Выражение содержит только целые цифры и знаки арифметических операций +-
         При вычислении должны учитываться приоритеты операций, например, результат вычисления выражения "2+3*2"
         должен быть равен 8. По оригинальному условию задачи все числа содержат не более одной цифры, пробелов в строке нет.*/

    public static int calculate(String expression) {

        String str = expression; // создание символьной строки
        int i = Integer.valueOf(str.substring(0, 1)); //первый элемент
        //if (i == Integer.valueOf(str.substring(0), Integer.parseInt("-"))) return Integer.parseInt("-" +i);
        if (str.length()== 1) return i;
        String s = str.substring(1, 2); // операнд (для манипуляций calculate)

        while (s.equals("*") || s.equals("/")) {   // логика вычислений
            if (s.equals("*")) {

                i *= Integer.valueOf(str.substring(2, 3)); //множитель

            } else if (s.equals("/"))
                i /= Integer.valueOf(str.substring(2, 3)); //процедура calculate деления: i делится на делитель с index 2
            str = str.substring(2);                        //Строка начинается с Подстроки  с символа по указанному индексу 2 до конца строки
            if (str.length() == 1) return i;               //вывод результата
            s = str.substring(1, 2);                      //указатель на index операнда
        }
        if (s.equals("+")) {
            i += calculate(str.substring(2));            //calculate процедура сложения
        } else if (s.equals("-"))
            i -= calculate(str.substring(2));            //calculate процедура вычитания
        return i;                                        // результат

    }
}
