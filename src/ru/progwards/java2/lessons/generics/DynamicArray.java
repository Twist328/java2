package ru.progwards.java2.lessons.generics;

public class DynamicArray<T> {

    int blocksize;
    T[] array;
    int size;

    public DynamicArray(int initsize, int blocksize) {

        array = (T[])new Object[initsize];
        this.blocksize = blocksize;
        size = 0;
    }

    public void add(T item) {

        if (size >= array.length) {
            T[] newArray = (T[])new Object[array.length + blocksize];
            copyData(array, newArray);
            array = newArray;
        }
        array[size++] = (T) item;
    }

    void copyData(T[] src, T[] dst) {

        for(int i=0; i<src.length; i++)
            dst[i] = src[i];
    }

    public T get(int index) {
        return array[index];
    }

    public int size() {
        return size;
    }
}