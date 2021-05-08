package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import ru.progwards.java2.lessons.tests.calc.*;
import java.util.*;
import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class SimpleCalculatorMult {

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

    @Parameterized.Parameters(name = "mult({0}*{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 2, 2},
                {5, 0, 0},
                {10, -15, -150},
                {-14, 19, -266},
                {0, 0, 0},
                {-100, 0, 0}
        });
    }

    @Test
    public void testMultNum() {
        int res = calc.mult(A, B);
        assertEquals(expected, res);
    }
}
