package ru.progwards.java2.lessons.generics;

import java.util.ArrayList;

public class DinamicArray<T> {

    ArrayList<T[]> data;
    int blocksize;
    int[] array;
    int size;

    public DinamicArray(int blocksize) {
        data = new ArrayList<T[]>();
        T array = (T) new int[blocksize];
        data.add((T[]) array);
        this.blocksize = blocksize;
        size = 0;
    }

    public void add(T item) {
        if (size >= array.length) {
            T array = (T) new int[blocksize];
            data.add((T[]) array);
            size = 0;
        }
       array[size++] = (int) item;
    }

    public T get(int index) {
        int index1 = index / blocksize;
        int index2 = index % blocksize;
        return (T) data.get(index1)[index2];
    }

    public int size() {
        return data.size() * blocksize - (blocksize - size);
    }
}