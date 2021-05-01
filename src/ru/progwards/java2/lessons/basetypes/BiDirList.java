package ru.progwards.java2.lessons.basetypes;

import java.util.*;

public class BiDirList<T> implements Iterable<T> {
    /*
        Реализовать класс BiDirList - двунаправленный связный список
Методы:
1.1 public void addLast(T item) - добавить в конец списка
1.2 public void addFirst(T item)- добавить в начало списка
1.3 public void remove(T item) - удалить
1.4 public T at(int i) - получить элемент по индексу
1.5 public int size() - получить количество элементов
1.6 public static<T> BiDirList<T> from(T[] array) - конструктор из массива
1.7 public static<T> BiDirList<T> of(T...array) -  конструктор из массива
1.8 public void toArray(T[] array) - скопировать в массив
1.9 реализовать интерфейс Iterable<T>
     */
    public static class Element<T> {
        T item;
        Element previous;
        Element next;

        Element(Element<T> previous, T item, Element<T> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }

    int size;           // размер списка
    Element<T> firstEl;  // первый элемент списка
    Element<T> lastEl;   // последний элемент списка

    BiDirList() {
        size = 0;
        firstEl = null;
        lastEl = null;
    }


    public void addLast(T item) {// добавить в конец списка
        Element<T> element = new Element(lastEl, item, null);
        if (lastEl != null)
            lastEl.next = element;
        else
            firstEl = element;
        lastEl = element;
        size++;
    }


    public void addFirst(T item) {// добавить в начало списка
        Element<T> element = new Element(null, item, firstEl);
        if (firstEl != null)
            firstEl.previous = element;
        else
            lastEl = element;
        firstEl = element;
        size++;
    }


    public void remove(T item) {// удалить
        Element<T> element = firstEl;
        while (element != null) {
            if (element.item == item) {

                if (element.previous == null)
                    firstEl = element.next;
                else
                    element.previous.next = element.next;

                if (element.next == null)
                    lastEl = element.previous;
                else
                    element.next.previous = element.previous;
                size--;
            }
            element = element.next;
        }
    }


    public T at(int i) {   // получить элемент по индексу
        if (i < 0 || i > size) return null;
        int a = 0;
        Element<T> element = firstEl;
        while (element != null && a != i) {
            element = element.next;
            a++;
        }
        return element.item;
    }


    public int size() {// получить количество элементов
        return size;
    }


    public static <T> BiDirList<T> from(T[] array) {// конструктор из списка
        BiDirList<T> res = new BiDirList<T>();
        for (T arr : array) {
            res.addLast(arr);
        }
        return res;
    }


    public static <T> BiDirList<T> of(T... array) {// конструктор из массива
        BiDirList<T> list = new BiDirList<T>();
        for (T arr : array) {
            list.addLast(arr);
        }
        return list;
    }


    public void toArray(T[] array) {
        array = (T[]) new Object[size];//скопировать в массив
        int i = 0;
        Element<T> element = firstEl;
        while (element != null) {
            array[i++] = element.item;
            element = element.next;
        }
        //return array;
    }

    public Iterator<T> getIterator() {//реализовать интерфейс Iterable<T>
        return new LIterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }

    public Iterator<T> iterator() {
        return new LIterator();
    }

    private class LIterator implements Iterator<T> {
        private Element<T> prev;

        LIterator() {
            prev = null;
        }

        /*public boolean hasNext() {
            return last.equals(null)|| last.next != null;
        }*/

        @Override
        public boolean hasNext() {
            return prev == (null) || prev.next != null;
        }

        @Override
        public T next() {
            prev = prev == null ? firstEl : prev.next;
            return prev.item;
        }
    }

    @Override
    public String toString() {
        return "BiDirList{" +
                "size=" + size +
                ", firstEl=" + firstEl +
                ", lastEl=" + lastEl +
                '}';
    }

    public static void main(String[] args) {
        BiDirList<Integer> a = new BiDirList<Integer>();

        a.addLast(3);
        a.addFirst(25);
        a.remove(3);
        a.addLast(3);
        a.addFirst(21);
        a.addLast(20);

        System.out.println("Поиск по индексу 3 = " + a.at(3));
        //System.out.println(Arrays.asList(new BiDirList[]{a}));
        System.out.println("________________________________");

        for (Integer i : a) {
            System.out.println(i);
            //System.out.println(new BiDirList<>().addLast(biDirList1));
        }
    }
}



