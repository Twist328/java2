package ru.progwards.java2.lessons.annotation;

import static org.junit.Assert.*;

public class CalculatorT {
    static Calculator CALCULATOR;

    @Before
    public void init() {
        CALCULATOR = new Calculator();
        System.out.println("\n********************************************");
        System.out.println("Запускается метод с аннотацией @Before");
        System.out.println("********************************************");
    }

    @Test(priority = 10)
    public void testSum10() {
        int a = 33;
        int b = 28;
        assertEquals(a+b, CALCULATOR.sum(a, b));
        System.out.println("Сложение " + a + " + " + b + " = 61 тест пройден");
        System.out.println("********************************************");
    }

    @Test(priority = 3)
    public void testDiff3() {
        int a = 28;
        int b = 37;
        assertEquals(a-b, CALCULATOR.different(a, b));
        System.out.println("Вычитание " + a + " - " + b + " = - 9 тест пройден");
        System.out.println("********************************************");
    }

    @Test(priority = 6)
    public void testMult6() {
        int a = 12;
        int b = 13;
        assertEquals(a*b, CALCULATOR.multiply(a, b));
        System.out.println("Умножение " + a + " * " + b + "= 156 тест пройден");
        System.out.println("********************************************");
    }

    @Test(priority = 5)
    public void testDiff5() {
        int a = 35;
        int b = 7;
        assertEquals(a-b, CALCULATOR.different(a, b));
        System.out.println("Вычитание " + a + " - " + b + " = 28 тест пройден");
        System.out.println("********************************************");
    }

    @Test(priority = 8)
    public void testMult8() {
        int a = 34;
        int b = 9;
        assertEquals(a*b, CALCULATOR.multiply(a, b));
        System.out.println("Умножение " + a + " * " + b + " = 306 тест пройден");
        System.out.println("********************************************");
    }

    @Test(priority = 2)
    public void testMult2() {
        int a = 47;
        int b = 5;
        assertEquals(a*b, CALCULATOR.multiply(a, b));
        System.out.println("Умножение " + a + " * " + b + " = 235 тест пройден");
        System.out.println("********************************************");
    }

    @Test(priority = 9)
    public void testDiv9() {
        int a = 124;
        int b = 4;
        assertEquals(a/b, CALCULATOR.divid(a, b));
        System.out.println("Деление " + a + " / " + b + " = 31 тест пройден");
        System.out.println("********************************************");
    }

    @Test(priority = 4)
    public void testDiv4() {
        int a = 51;
        int b = 17;
        assertEquals(a/b, CALCULATOR.divid(a, b));
        System.out.println("Деление " + a + " / " + b + " = 3 тест пройден");
        System.out.println("********************************************");
    }

    @Test(priority = 7)
    public void testDiv7() {
        int a = 96;
        int b = 16;
        assertEquals(a/b, CALCULATOR.divid(a, b));
        System.out.println("Деление " + a + " / " + b + " = 6 тест пройден");
        System.out.println("********************************************");
    }

    @Test(priority = 1)
    public void testSum() {
        int a = 1;
        int b = 2;
        assertEquals(a+b, CALCULATOR.sum(a, b));
       // System.out.println("********************************************");
        System.out.println("Сложение " + a + " + " + b + " = 3 тест пройден");
        System.out.println("********************************************");
    }

    @After
    public void destroy() {
        CALCULATOR = null;
        System.out.println("Запускается метод с аннотацией @After");
        System.out.println("********************************************");
    }
}
