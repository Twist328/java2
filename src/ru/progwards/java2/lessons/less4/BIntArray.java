package ru.progwards.java2.lessons.less4;

public class BIntArray<Integer> {
    int[] array;
    int blocksize;
    int size;

    public BIntArray(int initsize, int blocksize) {
        array = new int[initsize];
        this.blocksize = blocksize;
        size = 0;
    }

    public void add(int item) {
        if (size >= array.length) {
            int[] newArray = new int[array.length + blocksize];
            copyData(array, newArray);
            array = newArray;
        }
        array[size++] = item;
    }

    void copyData(int[] src, int[] dst) {
        for(int i=0; i<src.length; i++)
            dst[i] = src[i];
    }

    public int get(int index) {
        return array[index];
    }

    public int size() {
        return size;
    }
}

