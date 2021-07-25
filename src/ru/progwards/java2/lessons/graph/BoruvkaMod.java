package ru.progwards.java2.lessons.graph;

import java.util.Comparator;
import java.util.List;

/**
 * Основные компоненты для хранения графа
 */
public class BoruvkaMod {

    /**
     * Вершины графа
     *
     * @param <N> объект, подвязанный к вершине
     * @param <E> объект, подвязанный к дуге графа
     */

   public static class Node<N, E> {
       public N info; // информация об узле
        public List<Edge<N, E>> in; // массив входящих ребер
        public List<Edge<N, E>> out; // массив исходящих ребер
        public Condition condition; // состояние узла (пометка для алгоритма)
        public Graph<N, E> graph; // в каком оставном дереве находится данный узел (пометка для алгоритма)

        public Node(N info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "node{" + info + '}';
        }
    }

    /**
     * Дуги графа
     *
     * @param <N> объект, подвязанный к вершине
     * @param <E> объект, подвязанный к дуге графа
     */
    public static class Edge<N, E> {
        E info; // информация о ребре
        Node<N, E> out; // вершина, из которой исходит ребро
        Node<N, E> in; // вершина, в которую можно попасть
        // по этому ребру
        double weight; // стоимость перехода
        public int id; // идентификатор узла (уникальный ключ) (пометка для алгоритма)
        public boolean calculate; // была ли проанализирована дуга

        public Edge(E info, Node<N, E> out, Node<N, E> in, double weight) {
            this.info = info;
            this.out = out;
            this.in = in;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "edge{info=" + info + ", weight=" + weight + "}";
        }

        /**
         * Получить узел дуги, отличный от входящего узла (не используется)
         *
         * @param arc
         * @return
         */
        public Node<N, E> Other(Node<N, E> arc) {
            return arc == in ? out : in;
        }

        /**
         * Получить узел дуги, не принадлежащий графу
         *
         * @param graph граф, с которым в данное ремя работаем
         * @return узел дуги, не принадлежащий графу {@code graph}
         */
        public Node<N, E> Other(Graph<N, E> graph) {
            return out.graph != graph ? out : (in.graph != graph ? in : null);
        }
    }

    /**
     * Граф (вершины и дуги)
     *
     * @param <N> объект, подвязанный к вершине
     * @param <E> объект, подвязанный к дуге графа
     */
    public static class Graph<N, E> {
        public List<Node<N, E>> nodes;
        public List<Edge<N, E>> edges;
        public Condition condition; // состояние узла (пометка для алгоритма)
        Thread t; // ссылка на рассчитывающий поток
        int id; // идентификатор узла (уникальный ключ) (пометка для алгоритма)

        public Graph(List<Node<N, E>> nodes, List<Edge<N, E>> edges) {
            this.nodes = nodes;
            this.edges = edges;
        }
    }

    /**
     * Компаратор для выстраивания дерева по возрастанию веса дуг
     */
    public static Comparator<Edge> edgesComparator = (e1, e2) -> {
        if (e1.weight != e2.weight)
            return e1.weight < e2.weight ? -1 : 1;
        return e1.id == e2.id ? 0 : (e1.id < e2.id ? -1 : 1);
    };

}

