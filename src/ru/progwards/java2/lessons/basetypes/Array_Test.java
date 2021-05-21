package ru.progwards.java2.lessons.basetypes;

public class Array_Test {
    static final int size = 3;

    public static void main(String[] args) {
        Array_Test mane = new Array_Test();
        String[] params = new String[size];

        mane.toArray(params);
        for (String s : params)
            System.out.println(s);
    }

    public void toArray(String[] array) {
        //array = new String[size];
        array[0] = "abc";
        array[1] = "def";
        array[2] = "ghj";
    }
}
