package ru.progwards.java2.lessons.basetypes;

import java.util.Objects;

public class IntegerHK implements HashValue {
    private int value;

    IntegerHK(int value) {
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
        IntegerHK key = (IntegerHK) o;
        return value == key.value;
    }
}

