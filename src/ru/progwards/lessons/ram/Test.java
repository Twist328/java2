package ru.progwards.lessons.ram;
public class Test {
    public static void main(String... args) {
        Calculator c = new Calculator();
        int a= 10;int b=15;
        System.out.println(c.sum(a,b));
    }
}
/*
C:\Projects\java2>"C:\Program Files\Java\jdk14.0.1\bin\javac" -sourcepath src -d out src/ru/progwards/java2/lessons/ram/Test.java
C:\Projects\java2>java -classpath .\out ru.progwards.java2.lessons.ram.Test
25
*/
