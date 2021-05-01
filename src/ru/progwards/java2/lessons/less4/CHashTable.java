package ru.progwards.java2.lessons.less4;

public class CHashTable<T> {
    class CHashItem<T> {

        private T item;
        private int key;
        private CHashItem<T> next;

        CHashItem(int key, T item) {
            this.key = key;
            this.item = item;
        }

        int getKey() {
            return key;
        }

        T getItem() {
            return item;
        }

        void setNext(CHashItem<T> item) {
            next = item;
        }

        CHashItem<T> getNext() {
            return next;
        }

        boolean equals(CHashItem<T> item) {
            return key == item.key;
        }
    }

    CHashItem<T>[] table;

    CHashTable(int n) {
        table = new CHashItem[n];
    }

    public int getHash(int key) {
        return key % table.length;
    }

    void add(int key, T item) {
        int index = getHash(key);
        CHashItem<T> li = new CHashItem<>(key, item);
        CHashItem<T> head = table[index];
        table[index] = li;
        if (head != null)
            li.setNext(head);
    }

    T get(int key) {
        int index = getHash(key);
        CHashItem<T> current = table[index];
        do {
            if (current.key == key)
                return current.getItem();
            current = current.getNext();
        } while (current != null);
        return null;
    }
}

