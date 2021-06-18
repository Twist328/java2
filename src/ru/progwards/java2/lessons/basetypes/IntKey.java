package ru.progwards.java2.lessons.basetypes;

public class IntKey implements HashValue {
    private int value;

    IntKey(int value) {
        this.value = value;
    }

    @Override
    public int getHash() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntKey key = (IntKey) o;
        return value == key.value;
    }
}

