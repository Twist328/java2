package ru.progwards.java2.lessons.basetypes;


import java.util.Iterator;

public class MainHash {
    public static void main(String[] args) {
        DoubleHashTable<IntegerHK, Integer> doubleHashTable = new DoubleHashTable<>();

        for (int i = 377; i < 500; i++) {
            doubleHashTable.add(new IntegerHK(i), i);
        }

        System.out.println(doubleHashTable.get(new IntegerHK(400)));

        doubleHashTable.change(new IntegerHK(400), new IntegerHK(450));

        System.out.println(doubleHashTable.get(new IntegerHK(400)));

        System.out.println(doubleHashTable.get(new IntegerHK(450)));

        doubleHashTable.add(new IntegerHK(1222), 1222);

        doubleHashTable.change(new IntegerHK(377), new IntegerHK(1222));

        System.out.println(doubleHashTable.size());

        System.out.println(doubleHashTable.get(new IntegerHK(794)));

        System.out.println(doubleHashTable.get(new IntegerHK(1222)));
        System.out.println(doubleHashTable.get(new IntegerHK(377)));

        Iterator<DoubleHashTable.Node<IntegerHK, Integer>> iterator = doubleHashTable.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


        DoubleHashTable<StringHK, Integer> hashTable = new DoubleHashTable<>();

        hashTable.add(new StringHK("Дама"), 10);
        hashTable.add(new StringHK("Замок"), 21);
        hashTable.add(new StringHK("Джентльмен"), 567);
        hashTable.add(new StringHK("Печенье"), 1);
        hashTable.add(new StringHK("Банка"), 23);
        hashTable.add(new StringHK("Hello, world!"), 98);
        hashTable.add(new StringHK("Нина"), 67);
        hashTable.add(new StringHK("Парик"), 111);
        hashTable.add(new StringHK("Петербург"), 2236);
        hashTable.add(new StringHK("Мишка"), 568);
        hashTable.add(new StringHK("Праздник"), 23365);
        hashTable.add(new StringHK("Игрушка"), 235);
        hashTable.add(new StringHK("Изделие"), 83);

        Iterator<DoubleHashTable.Node<StringHK, Integer>> iterator1 = hashTable.getIterator();

        while (iterator1.hasNext())
            System.out.println(iterator1.next());

        System.out.println(hashTable.get(new StringHK("Банка")));
    }
}
