package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.*;
import ru.progwards.java2.lessons.tests.calc.*;

public class SimpleCalcDivZero {
    static SimpleCalculator calc;

    @BeforeClass
    public static void init() {
        calc = new SimpleCalculator();
    }

    @Test(expected = ArithmeticException.class)
    public void testDivByZero() {
        int result = calc.div(5, 0);
    }
}
