package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import ru.progwards.java2.lessons.tests.calc.*;
import java.util.*;
import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class SimpleCalculatorSum {

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

    @Parameterized.Parameters(name = "sum({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{

                {1, 3, 4},
                {-150, 33, -117},
                {4, -2, 2},
                {-7, -5, -12},
                {10, -10, 0},
                {1, 0, 1},
                {0, 0, 0},
                {-1, 0, -1}
        });
    }

    @Test
    public void testAddNum() {
        long res = calc.sum(A, B);
        assertEquals(expected, res);

    }
}
