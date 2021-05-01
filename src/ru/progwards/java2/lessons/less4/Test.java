package ru.progwards.java2.lessons.less4;

public class Test {
    public static void main(String[] args) {
        OList<Integer> list = new OList<>();
        list.add(2);
        list.add(1);
        list.add(5);
        OList<Integer>.ListItem<Integer> current = list.getHead();
        while(current != null) {
            System.out.println(current.getItem());
            current = current.getNext();
        }
    }
}