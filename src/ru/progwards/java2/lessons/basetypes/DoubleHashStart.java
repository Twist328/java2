package ru.progwards.java2.lessons.basetypes;

interface HashValue {
    int getHash();
}




class Value implements HashValue {     //реализация значения ключа ключа(V)
    private final int v;
    public Value(int value) {
        this.v = value;
    }
    public static Value of(int value) {
        return new Value(value);
    }
    public String toString() {
        return "{" + v + '}';
    }
    public int getHash(){
        return v;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value = (Value) o;
        return v == value.v;
    }
}

interface DoubleHashStart {


    int apply(int hash, int size, int coll);
}
