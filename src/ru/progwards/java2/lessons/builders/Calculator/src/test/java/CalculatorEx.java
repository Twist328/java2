import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import java.util.*;

@RunWith(Parameterized.class)
public class CalculatorEx {


    @Parameterized.Parameter
    public String expression;


    @Parameterized.Parameters(name = "calculate({0}}) = exception")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(1+2"},
                {"(333-244"},
                {"22*1W"},
                {"c.t"},
                {"22/1(@@"},
                {"(21-4("},
                {"(1-8)+(2+2(#"},
                {"(23*1-(7+6)="}
        });
    }

    @Test(expected = Exception.class)
    public void test_exceptions() throws Exception {
        Calculator.calculate(expression);
    }

}

