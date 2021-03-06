package ru.progwards.java2.lessons.graph;

import java.util.List;

public interface IBoruvka<N, E> {
    public List<BoruvkaMod.Edge<N, E>> getMinEdgeTree(BoruvkaMod.Graph<N, E> graph);
}
