package ru.progwards.lessons.ram;



public class Test {
    public static void main(String[] args) {
        Calculator c = new Calculator();
        System.out.print(c.sum(10,10));
    }
}

/*
C:\Projects\java2>"C:\Program Files\Java\jdk14.0.1\bin\javac" -sourcepath src -d out src/ru/progwards/java2/lessons/ram/Test.java
C:\Projects\java2>java -classpath .\out ru.progwards.java2.lessons.ram.Test
25
*/
