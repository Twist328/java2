
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import java.util.*;


import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class CalculatorA {


    @Parameterized.Parameter(0)
    public String expression;

    @Parameterized.Parameter(1)
    public int expected;


    @Parameterized.Parameters(name = "calculate({0}}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1+0",  1},
                {"1-9",  -8},
                {"2*2",  4},
                {"9/3",  3},
                {"5/1",  5},
                {"2*8", 16},
                {"(1-1)+(2+2)", 4},
                {"(3/2)-(6*6)", -35},
                {"(8-2)*(7+(2*3)/(8-6*4*1/9/2))", 42},
                {"(4+3)*0)/9)", 0}
        });
    }

    @Test
    public void testall() throws Exception {
        int res = Calculator.calculate(expression);
        assertEquals(expected, res);
    }

}

