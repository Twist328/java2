import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CalculatorEx {


    @Parameterized.Parameter
    public static String INFO;


    @Parameterized.Parameters(name = "calculate({0}}) = exception")
    public static Collection<Object[]> record() {
        return Arrays.asList(new Object[][]{
                {"(1+2"},
                {"(333-244"},
                {"22*1W"},
                {"c.t"},
                {"22/1(@@"},
                {"(21-4("},
                {"(1-8)+(2+2(#"},
                {"(23*1-(7+6)="},
                {"23/(10-10)"}
        });
    }

    @Test(expected = Exception.class)
    public void testExceptions() throws Exception {
        Calculator.calc(INFO);
    }

}
