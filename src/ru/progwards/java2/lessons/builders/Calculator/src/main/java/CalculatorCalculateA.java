import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

        import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class CalculatorCalculateA {

    static Calculator calculate;

    @BeforeClass
    public static void init() {
        calculate = new Calculator();
    }

    @Parameterized.Parameter(0)
    public int A;

    @Parameterized.Parameter(1)
    public int B;

    @Parameterized.Parameter(2)
    public int expected;

    @Parameterized.Parameters(name = "calculate({0}}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{

                {1, 3, 4},
                {-150, 33, -117},
                {4, -2, 2},
                {-7, -5, -12},
                {10, -10, 0},
                {1, 0, 1},
                {0, 0, 0},
                // {3/1,  2},
                {-1, 0, -1}
        });
    }

    @Test
    public void testAddNum() {
        var res = calculate.sum(A, B);
        assertEquals(expected, res);
    }
    @Parameterized.Parameters(name = "diff({0}-{1}) = {2}")
    public static Collection<Object[]> data1() {
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
        var res1 = calculate.diff(A, B);
        assertEquals(expected, res1);
    }
}
