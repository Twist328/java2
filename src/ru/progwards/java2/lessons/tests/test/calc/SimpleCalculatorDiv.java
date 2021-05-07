package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.progwards.java2.lessons.tests.calc.SimpleCalculator;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class SimpleCalculatorDiv {

    static SimpleCalculator calc;

    @BeforeClass
    public static void init() {
        calc = new SimpleCalculator();
    }

    @Parameterized.Parameter(0)
    public int A;

    @Parameterized.Parameter(1)
    public int B;

    @Parameterized.Parameter(2)
    public int expected;

    @Parameterized.Parameters(name = "div({0}/{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 1, 1},
                {3, -3, -1},
                {-10, 2, -5},
                {-25, -7, 3},
                {1, 1, 1},
                {0, 1, 0}
        });
    }

    @Test
    public void testDivNum() {
        long res = calc.div(A, B);
        assertEquals(expected, res);
    }
}

