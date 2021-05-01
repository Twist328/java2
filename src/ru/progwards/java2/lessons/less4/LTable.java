package ru.progwards.java2.lessons.less4;

import java.util.Arrays;
import java.util.Iterator;

public class LTable<T> {

    class TableItem<T> {

        private T item;
        private int key;

        TableItem(int key, T item) {
            this.key = key;
            this.item = item;
        }

        int getKey() {
            return key;
        }

        T getItem() {
            return item;
        }

        public String toString() {
            return key+":"+item.toString();
        }
    }

    Object[] table;
    int depth = 10;

    LTable(int n) {
        table = new Object[n];
    }

    public int getHash(int key) {
        return key % table.length;
    }

    public void add(int key, T item) {
        int index = getHash(key);
        int i = 0;
        while (table[index+i] != null) {
            i++;
            if (index+i >= table.length || i >= depth)
                throw new ArrayIndexOutOfBoundsException();
        }
        table[index+i] = new TableItem<T>(key, item);
    }

    public T get(int key) {
        int index = getHash(key);
        int i = 0;
        while (table[index+i] != null && ((TableItem<T>)(table[index+i])).getKey() != key) {
            i++;
            if (index+i >= table.length || i >= depth)
                return null;
        }
        if (table[index+i] == null)
            return null;
        return ((TableItem<T>)(table[index+i])).getItem();
    }

    public String toString() {
        return Arrays.toString(table);
    }

    public Iterator<LTable<T>> getIterator() {
        return null;
    }
}

