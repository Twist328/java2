package ru.progwards.java2.lessons.less4;

public class Test7 {
    public static void main(String[] args) {
        LTable<String> table = new LTable<>(10);
        for (int i=0; i<100; i+=10)
            table.add(i, "i="+i);

        System.out.println(table.get(2));
        for (int i=0; i<100; i+=10)
            System.out.println(table.get(i));
    }
}

