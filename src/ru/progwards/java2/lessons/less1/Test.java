package ru.progwards.java2.lessons.less1;

public class Test {
    static int sumSequence(int n) {
        if (n == 1)
            return n;
        return sumSequence(n - 2) + n;
    }

    public static void main(String[] args) {
        Calculator c = new Calculator();

        System.out.println(c.sum(10, 15));
        //System.out.println(sumSequence(2));
        System.out.println(sumSequence(1));
        System.out.println(sumSequence(3));
    }

}




/*
C:\Projects\java2>"C:\Program Files\Java\jdk14.0.1\bin\javac" -sourcepath src -d out src/ru/progwards/java2/lessons/ram/Test.java
C:\Projects\java2>java -classpath .\out ru.progwards.java2.lessons.ram.Test
25
*/
