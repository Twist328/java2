package ru.progwards.java2.lessons.recursion.less2;

public class Factorial {  //Факториал через рекурсию
    public static int factorial(int val){
        // условие выхода
        if (val <= 1)
            return 1;
        // рекурсия (n - 1)! * n
        return factorial/*Вызов функцией самой себя Рекурсия*/(val - 1) * val;
    }
    public static void main(String[] args) {
        System.out.println(factorial(5));
    }
}

