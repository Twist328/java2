package ru.progwards.java2.lessons.less10;

import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AnnotationTest {
    String text() default "   Всегда говори привет";
}

public class Greetings {

    //реализуйте метод с сигнатурой void printAnnotation(),
    // который печатает на консоль для класса Greetings название метода,
    // и значение text аннотации AnnotationTest, если таковое определено

    @AnnotationTest
    void test1() {
    }
    @AnnotationTest(text ="   Если не мы то кто?")
    void test2() {
    }
    @AnnotationTest(text="   Никогда не говори - : Никогда!")
    void test3() {
    }
    @AnnotationTest(text ="   Не говори ПРОЩАЙ, говори ДО СВИДАНИЯ! ")
    void test4() {

    }
    void printAnnotation() {
        Class<Greetings> clazz = Greetings.class;

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation a: annotations)
                if(a.annotationType()==AnnotationTest.class)
                    System.out.println(method.getName() + " "+((AnnotationTest)a).text());
        }
    }

    public static void main(String[] args) {
        new Greetings().printAnnotation();
    }
}