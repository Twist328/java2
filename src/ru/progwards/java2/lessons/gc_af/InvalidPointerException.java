package ru.progwards.java2.lessons.gc_af;

public class InvalidPointerException extends Exception
{
    public InvalidPointerException()
    {
        super("А указатель-то нЭправильный!");
    }
}
