package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.progwards.java2.lessons.tests.calc.SimpleCalculator;

public class CheckExceptionDiffTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Test
    public void ExceptionWithDiff() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Int range overflow during on subtraction " + ((long)Integer.MIN_VALUE - 1));

        SimpleCalculator.diff(Integer.MIN_VALUE, 1);
    }

}


