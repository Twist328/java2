package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.*;
import org.junit.rules.ExpectedException;
import ru.progwards.java2.lessons.tests.calc.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckExceptionSumTest {

        @Rule
        public ExpectedException exception = ExpectedException.none();
        @Test
        public void ExceptionWithSum() {
            exception.expect(RuntimeException.class);
            exception.expectMessage("Int range overflow during addition " + ((long)Integer.MAX_VALUE + 1));

            SimpleCalculator.sum(Integer.MAX_VALUE, 1);
        }

    }


