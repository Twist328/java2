package ru.progwards.java2.lessons.graph;

import java.util.*;

import static ru.progwards.java2.lessons.graph.BoruvkaMod.*;
import static ru.progwards.java2.lessons.graph.Condition.*;

/**
 * Нахождение минимального оставного дерева по алгоритму Борувки
 *
 * Основа для очерди рёбер - TreeSet
 * По тестам, где рёбер в 10 раз больше чем вершин - выгоднее PriorityQueue, по памяти - одинаково
 */
public class Boruvka<N, E> implements IBoruvka<N, E> {

    /**
     * Брать ли дуги, исходящие из узла
     * Получится, мы сможем из какой-то одной вершины пройти до всех остальным по исходящим дугам
     */
    final static boolean EXPORT = true;

    /**
     * Брать ли дуги, входящие в узел
     * Если будет установлен {@code takeOuts} и {@code takeIns} то может не найтись вершины, из готорой сможем обойти весь граф по стрелкам
     * Если оба параметра будут {@code false}, то дуги найдены не будут
     * Если {@true} имеет только один параметр, очень важно, с какой веришины начинаем обход(от расположения в графе), т.к. дерево может получиться не минимальным
     *
     * @see Boruvka#takeOuts
     */
    final static boolean TAKE_INSIDE = true;

    /**
     * Выводить ли отладочную информацию в процессе обработки
     */
    final static boolean WISHDRAW_AMEND = false;

    /**
     * Вычисляет минимальное остовное дерево в виде списка дуг графа
     *
     * @param graph
     * @param <N> Тип "Узел"
     * @param <E> Тип "Ребро"
     * @return минимальное остовное дерево в виде списка дуг графа
     */
    static <N, E> List<Edge<N, E>> minTree(Graph<N, E> graph) {
        Boruvka<N, E> alg = new Boruvka<N, E>();
        return alg.getMinEdgeTree(graph);
    }

    /**
     * Вычислить дуги, требующиеся для построения минимального остовного дерева
     *
     * @return список дуг, требующихся для построения минимального остовного дерева
     */
    public List<Edge<N, E>> getMinEdgeTree(Graph<N, E> graph) {
        List<Edge<N, E>> result = new ArrayList<Edge<N, E>>(graph.nodes.size());
        ArrayList<Graph<N, E>> graphs = new ArrayList<Graph<N, E>>(graph.nodes.size());

        // генерация остовных деревьев
        for (Node n : graph.nodes) {
            n.condition = NO_METRIC; // белый
            Graph<N, E> g = new Graph<N, E>(new ArrayList<Node<N, E>>(), List.of());
            n.graph = g;
            g.nodes.add(n);
            graphs.add(g);
        }
        // проидентифицируем дуги
        int id = 0;
        for (Edge e : graph.edges)
            e.id = id++;

        for (Graph<N, E> g : graphs)
            if (checkGraphNotUsed(g))
                Find(result, g);
        return result;
    }

    /**
     * Проверить, рассчитаны ли какие-либо элементы из заданного графа
     *
     * @param g проверяемый граф
     * @return true, если ни один из элементов графа не рассчитывался
     */
    private boolean checkGraphNotUsed(Graph<N, E> g) {
        for (Node n : g.nodes)
            if (n.condition != NO_METRIC)
                return false;
        return g.nodes.size() > 0;
    }

    /**
     * Генерация оставного леса из дерева подсоединением других оставных лесов
     * В первую очередь соединяемся по дугам с минимальным весом
     *
     * @param result список дуг оставного леса (пополняется)
     * @param g      остoвной лес, который анализируем сейчас (растёт по мере объединения)
     */
    private void Find(List<Edge<N, E>> result, Graph<N, E> g) {
        Node baseNode = g.nodes.get(0);
        if (WISHDRAW_AMEND) System.out.println("Find(" + baseNode + ")");
        baseNode.condition = IN_WEAR; // серый

        // собираем дерево из исходящих дуг
        TreeSet<Edge<N, E>> referenced = new TreeSet<Edge<N, E>>(edgesComparator);
        for (Node<N, E> n : g.nodes) {
            if (EXPORT)
                for (Edge<N, E> e : n.out)
                    referenced.add(e);
            if (TAKE_INSIDE)
                for (Edge<N, E> e : n.in)
                    referenced.add(e);
        }

        // перебираем по порядку все дуги
        while (!referenced.isEmpty()) {
            if (WISHDRAW_AMEND) {
                System.out.print("References in queue: ");
                referenced.stream().forEach(System.out::print);
                System.out.println();
            }
            Edge<N, E> e = referenced.pollFirst();
            e.calculate = true;
            Node<N, E> n = e.Other(g); // узел не в графе
            if (n != null) {
                Merge(g, n.graph, referenced);
                result.add(e);
            }
        }
        baseNode.condition = CALL; // черный
    }

    /**
     * Объединяем два графа в один, который указан первым
     *
     * @param graph1 граф-приёмник
     * @param graph2 граф-источник (опустошаемый)
     */
    private void Merge(Graph<N, E> graph1, Graph<N, E> graph2, TreeSet<Edge<N, E>> referenced) {
        if (WISHDRAW_AMEND) System.out.println("Merge(" + graph1.nodes.get(0) + "," + graph2.nodes.get(0) + ")");
        for (Node<N, E> n : graph2.nodes) {
            //boolean isNewRefs = n.status != IN_USE;
            // добавим новые дуги в сравнение, если узел не обработан
            if (n.condition != CALL) {
                if (EXPORT)
                    for (Edge e : n.out)
                        if (!e.calculate) {
                            referenced.add(e);
                            if (WISHDRAW_AMEND) System.out.print("  add " + e);
                        }
                if (TAKE_INSIDE)
                    for (Edge e : n.in)
                        if (!e.calculate) {
                            referenced.add(e);
                            if (WISHDRAW_AMEND) System.out.print("  add " + e);
                        }
                if (WISHDRAW_AMEND) System.out.println();

                n.condition = CALL;
            }
            // перенесем узел в первый граф
            graph1.nodes.add(n);
            n.graph = graph1;
        }
        // очистим граф-источник
        graph2.nodes.clear();
    }

    /**
     * Test оптимального вычисления  дерева из 7 узлов
     */
    public static void main(String[] args) {
        Node<String, String> A = new Node<>("A");
        Node<String, String> B = new Node<>("B");
        Node<String, String> C = new Node<>("C");
        Node<String, String> D = new Node<>("D");
        Node<String, String> E = new Node<>("E");
        Node<String, String> F = new Node<>("F");
        Node<String, String> G = new Node<>("G");
        Edge<String, String> e1 = new Edge<>("a-b", A, B, 0);
        Edge<String, String> e2 = new Edge<>("a-c", A, C, 2);
        Edge<String, String> e3 = new Edge<>("a-d", A, D, 7);
        Edge<String, String> e4 = new Edge<>("b-e", B, E, 10);
        Edge<String, String> e5 = new Edge<>("b-g", B, G, 5);
        Edge<String, String> e6 = new Edge<>("c-d", C, D, 5);
        Edge<String, String> e7 = new Edge<>("c-f", C, F, 1);
        Edge<String, String> e8 = new Edge<>("d-g", D, G, 0);
        Edge<String, String> e9 = new Edge<>("e-d", E, D, 0);
        Edge<String, String> e10 = new Edge<>("f-g", F, G, 4);
        Edge<String, String> e11 = new Edge<>("g-e", G, E, 0);
        A.out = List.of(e1, e2, e3);
        A.in = List.of();
        B.out = List.of(e4, e5);
        B.in = List.of(e1);
        C.out = List.of(e6, e7);
        C.in = List.of(e2);
        D.out = List.of(e8);
        D.in = List.of(e3, e6, e9);
        E.out = List.of(e9);
        E.in = List.of(e4, e11);
        F.out = List.of(e10);
        F.in = List.of(e7);
        G.out = List.of(e11);
        G.in = List.of(e5, e8, e10);
        Graph<String, String> graph = new Graph<>(
                List.of(F, E, C, B, D, A, G),
                List.of(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11)
        );
        List<Edge<String, String>> result = minTree(graph);
        System.out.println("\n*******************************************************************************************");
        System.out.println(result);
        System.out.println("*******************************************************************************************");
    }
}


