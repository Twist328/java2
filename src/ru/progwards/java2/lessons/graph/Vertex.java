package ru.progwards.java2.lessons.graph;

public class Vertex implements Comparable<Vertex> {
    private int number;
    private int mark;

    public Vertex(int number, int mark) {
        this.number = number;
        this.mark = mark;
    }

    public int getNumber() {
        return number;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public int compareTo(Vertex o) {
        return Integer.compare(mark, o.mark);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return number == vertex.number &&
                mark == vertex.mark;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "number=" + number +
                ", mark=" + mark +
                '}';
    }
}
