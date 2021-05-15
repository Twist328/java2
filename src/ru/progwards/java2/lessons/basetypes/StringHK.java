package ru.progwards.java2.lessons.basetypes;

public class StringHK implements HashValue {
    public static final long INT_MAX = 4294967295L;

    private String value;

    StringHK(String value) {
        this.value = value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringHK key = (StringHK) o;
        return value.equals(key.value);
    }

    @Override
    public String toString() {
        return value;
    }
    private long unsignedInt(long num) {
        return num % INT_MAX;
    }

    @Override
    public int getHash() {
        long b = 378551;
        long a = 63689;
        int num = 0;
        for (int i = 0; i < value.length(); i++) {
            num = (int)unsignedInt(num * a + value.charAt(i));
            a = unsignedInt(a * b);
        }
        return Math.abs(num);

    }
}

