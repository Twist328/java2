package ru.progwards.java2.lessons.basetypes;


import java.util.Iterator;

public class MainHash {
    public static void main(String[] args) {
        DoubleHashTable<IntKey, Integer> doubleHashTable = new DoubleHashTable<>();

        for (int i = 377; i < 500; i++) {
            doubleHashTable.add(new IntKey(i), i);
        }

        System.out.println(doubleHashTable.get(new IntKey(400)));

        doubleHashTable.change(new IntKey(400), new IntKey(450));

        System.out.println(doubleHashTable.get(new IntKey(400)));

        System.out.println(doubleHashTable.get(new IntKey(450)));

        doubleHashTable.add(new IntKey(1222), 1222);

        doubleHashTable.change(new IntKey(377), new IntKey(1222));

        System.out.println(doubleHashTable.size());

        System.out.println(doubleHashTable.get(new IntKey(794)));

        System.out.println(doubleHashTable.get(new IntKey(1222)));
        System.out.println(doubleHashTable.get(new IntKey(377)));

        Iterator<DoubleHashTable.Node<IntKey, Integer>> iterator = doubleHashTable.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


        DoubleHashTable<StringKey, Integer> hashTable = new DoubleHashTable<>();

        hashTable.add(new StringKey("Дама"), 10);
        hashTable.add(new StringKey("Замок"), 21);
        hashTable.add(new StringKey("Джентльмен"), 567);
        hashTable.add(new StringKey("Печенье"), 1);
        hashTable.add(new StringKey("Банка"), 23);
        hashTable.add(new StringKey("Hello, world!"), 98);
        hashTable.add(new StringKey("Нина"), 67);
        hashTable.add(new StringKey("Парик"), 111);
        hashTable.add(new StringKey("Петербург"), 2236);
        hashTable.add(new StringKey("Мишка"), 568);
        hashTable.add(new StringKey("Праздник"), 23365);
        hashTable.add(new StringKey("Игрушка"), 235);
        hashTable.add(new StringKey("Изделие"), 83);

        Iterator<DoubleHashTable.Node<StringKey, Integer>> iterator1 = hashTable.getIterator();

        while (iterator1.hasNext())
            System.out.println(iterator1.next());

        System.out.println(hashTable.get(new StringKey("Банка")));
    }
}
