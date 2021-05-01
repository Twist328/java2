package ru.progwards.java2.lessons.less4;

public class OList <T>{

    class ListItem<T> {

        private T item;
        private ListItem<T> next;

        ListItem(T item) {
            this.item = item;
        }

        T getItem() {
            return item;
        }

        void setNext(ListItem<T> item) {
            next = item;
        }

        ListItem<T> getNext() {
            return next;
        }

    }

    private ListItem<T> head;
    private ListItem<T> tail;

    ListItem<T> getHead() {
        return head;
    }

    void add(T item) {
        ListItem<T> li = new ListItem<T>(item);
        if (head == null) {
            head = li;
            tail = li;
        } else {
            tail.setNext(li);
            tail = li;
        }
    }
}

