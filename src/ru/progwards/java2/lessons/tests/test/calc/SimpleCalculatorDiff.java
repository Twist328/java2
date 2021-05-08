package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.*;
//import org.junit.Test;
import org.junit.runner.*;
import org.junit.runners.*;
import ru.progwards.java2.lessons.tests.calc.*;

import java.util.*;
//import java.util.Collection;


import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class SimpleCalculatorDiff {

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

    @Parameterized.Parameters(name = "diff({0}-{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 2, -1},
                {3, 0, 3},
                {-4, 2, -6},
                {-259, -5, -254},
                {-2, -2, 0},
                {0, 0, 0}
        });
    }

    @Test
    public void testDiffNum() {
        var res  = calc.diff(A, B);
        assertEquals(expected, res);

    }
}
