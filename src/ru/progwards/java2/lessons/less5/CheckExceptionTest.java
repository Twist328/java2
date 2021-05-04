package ru.progwards.java2.lessons.less5;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckExceptionTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Test
    public void SumWithException() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Переполнение диапазона int при сложении " + ((long)Integer.MAX_VALUE + 1));

        Calculator.sum(Integer.MAX_VALUE, 1);
    }

}
