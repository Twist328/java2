package ru.progwards.java2.lessons.less4;

public class EList {
    static class ListItem {

        private ListItem next;

        void setNext(ListItem item) {
            next = item;
        }

        ListItem getNext() {
            return next;
        }
    }

    private ListItem head;
    private ListItem tail;

    ListItem getHead() {
        return head;
    }

    void add(ListItem item) {
        if (head == null) {
            head = item;
            tail = item;
        } else {
            tail.setNext(item);
            tail = item;
        }
    }

    ListItem poll() {
        ListItem li = head;
        head = head.next; //head.getNext();
        return li;
    }

}

