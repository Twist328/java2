package ru.progwards.java2.lessons.less1;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Java ! This is console JAVA HelloWrorld.");
    }
}

/*
C:\Projects\java2>"C:\Program Files\Java\jdk14.0.1\bin\javac" -d out src/ru/progwards/java2/lessons/ram/HelloWorld.java
C:\Projects\java2>java -classpath .\out ru.progwards.java2.lessons.ram.HelloWorld
*/

/*
C:\Projects\java2>"C:\Program Files\Java\jdk14.0.1\bin\javac" -d out src/ru/progwards/java2/lessons/less1/HelloWorld.java
C:\Projects\java2>"C:\Program Files\Java\jdk14.0.1\bin\jar" cfe hello.jar HelloWorld out/ru/progwards/java2/lessons/less1/HelloWorld.
classC:\Projects\java2>"C:\Program Files\Java\jdk14.0.1\bin\java" -jar hello.jar
Error: Could not find or load main class HelloWorld              -- ??
*/
