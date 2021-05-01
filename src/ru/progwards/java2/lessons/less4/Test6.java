package ru.progwards.java2.lessons.less4;

public class Test6 {
    public static void main(String[] args) {
        CHashTable<String> table = new CHashTable<>(10);
        for (int i=0; i<100; i++)
            table.add(i, "i="+i);

        for (int i=0; i<100; i++)
            System.out.println(table.get(i));
    }
}

