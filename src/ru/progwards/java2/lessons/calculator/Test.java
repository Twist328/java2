package ru.progwards.java2.lessons.calculator;

import java.io.*;

public class Test {

    public static void main(String args[]){
        String str = new String("Добро пожаловать на Progwards.ru");

        System.out.print("Возвращаемое значение: ");
        System.out.println(str.substring(5));

        System.out.print("Возвращаемое значение: ");
        System.out.println(str.substring(5, 15));
    }
}
