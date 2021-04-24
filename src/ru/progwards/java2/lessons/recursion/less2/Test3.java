package ru.progwards.java2.lessons.recursion.less2;


    public class Test3 {

/*   Напишите метод с сигнатурой String reverseChars(String str),
     который возвращает символы строки str в обратном порядке.
     Т.е. если на входе "12345" на выходе должно быть "54321"

   Задачу надо решить методом рекурсии, циклы использовать нельзя!!!*/

        static String reverseChars(String str) {
            int a = str.length();
            if (a <= 1) return str;
            int b = a / 2;
            return reverseChars(str.substring(b)) + reverseChars(str.substring(0, b));
        }

        public static void main(String[] args) {
            System.out.println(reverseChars("12345"));
            System.out.println(reverseChars("лилипут сома на мосту пилил"));
            System.out.println(reverseChars("java объектно-ориентированный, строго типизированный язык программирования, разработанный компанией Oracle"));
        }
    }


