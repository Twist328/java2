package ru.progwards.java2.lessons.graph;

public class VertexOutOfBoundsException extends RuntimeException {
    private String respons;

    public VertexOutOfBoundsException(String respons) {
        this.respons = respons;
    }

    @Override
    public String toString() {
        return respons;
    }
}