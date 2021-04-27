package ru.progwards.java2.lessons.generics;

import java.util.Arrays;

class DynamicArray<T> {

    private T[] array = (T[]) new Object[10];
    int blocksize = array.length;
    int size;

    public DynamicArray(int initsize, int blocksize) {

        T array = (T) new Object[(int) initsize];
        this.blocksize = (int) blocksize;
        size = 0;
    }

    DynamicArray() {
    }

    public void add(T item) {// добавляет элемент num в конец массива
        if (size == array.length) {
            T[] newArr = (T[]) new Object[array.length + blocksize];
            copyData(array, newArr);
            array = newArr;
        }
        array[size++] = item;
    }

    void copyData(T[] src, T[] dst) {
        System.arraycopy(src, 0, dst, 0, src.length);
        dst = src;
    }

    public void insert(int pos, T item) {// добавляет элемент num в позицию pos массива
        T[] newArr = (T[]) new Object[array.length + 1];
        System.arraycopy(array, 0, newArr, 0, pos);
        System.arraycopy(array, pos, newArr, pos + 1, array.length - pos);
        newArr[pos] = item;
        array = newArr;
    }

    public void remove(int pos) {// удаляет элемент в позиции pos массива
        T[] newArr = (T[]) new Object[array.length - 1];
        System.arraycopy(array, 0, newArr, 0, pos);
        System.arraycopy(array, pos + 1, newArr, pos, array.length - pos - 1);
        array = newArr;
    }

    public T set(int pos) { // возвращает элемент по индексу pos
        return array[pos];
    }

    public void print() {
        System.out.println(this.toString());
    }

    public static void main(String[] args) {
        DynamicArray<Integer> a = new DynamicArray();
        a.print();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);
        a.add(6);
        a.add(7);
        a.add(8);
        a.add(9);
        a.add(10);
        a.print();
        a.insert(2, 3);
        a.print();
        a.remove(1);
        a.print();
        System.out.println("a[2] = " + a.set(2));
    }

    @Override
    public String toString() {
        return "DynamicArray{" +
                "array=" + Arrays.toString(array) +
                ", blocksize=" + blocksize +
                ", size=" + size +
                '}';
    }
}