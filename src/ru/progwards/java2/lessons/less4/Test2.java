package ru.progwards.java2.lessons.less4;

public class Test2 {
    public static class MyItem extends EList.ListItem {
        int num;
        MyItem(int num) {
            this.num = num;
        }
    }

    public static void main(String[] args) {
        EList list = new EList();
        list.add(new MyItem(2));
        list.add(new MyItem(1));
        list.add(new MyItem(5));
        MyItem current = (MyItem) list.getHead();
        while(current != null) {
            System.out.println(current.num);
            current = (MyItem) current.getNext();
        }
    }
}

