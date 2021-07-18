package ru.progwards.java2.lessons.less12;

import java.util.Arrays;
import java.util.TreeSet;

import static ru.progwards.java2.lessons.less12.State.*;


/**
 * Алгоритм Дейкстра на основе матрицы смежности
 */
public class Dijkstra {

    /**
     * Матрица смежности графа
     */
    int graph[][];
    /**
     * Количество вершин графа
     */
    int size;
    /**
     * Значение бесконечности infinity
     */
    final static int INFINITY = Integer.MAX_VALUE;

    Dijkstra(int[][] graph) {
        size = graph.length;
        if (size == 0)
            throw new UnsupportedOperationException("Граф должен быть не пуст.");
        for (int[] row : graph) {
            if (size != row.length)
                throw new UnsupportedOperationException("Ширина и высота матрицы должна быть одинаковой.");
        }
        this.graph = graph;
    }

    /**
     * Вершина
     */
    class Vertex {
        /**
         * Порядковый номер (с нуля)
         */
        int n;
        /**
         * Вес (сколько стоит добраться)
         */
        int weight;
        /**
         * Состояние
         *
         * @see State
         */
        State status;

        @Override
        public String toString() {
            return "Vertex{n=" + n + ", weight=" + weight + '}';
        }
    }

    /*
     * Алгоритм Дейсктры из вершины n
     * @param n Вершина, из которой надо построить маршруты
     * @return Матрицу вариантов минимальных маршрутов до всех вершин
     */
    public int[] find(int n) {
        // создадим пустой массив результатов, всем узлам - бесконечность
        int[] result = new int[size];
        Arrays.fill(result, INFINITY);
        // массив весов вершин
        Vertex V[] = new Vertex[size];
        for (int i = 0; i < size; i++) {
            V[i] = new Vertex();
            V[i].weight = INFINITY;
            V[i].n = i;
            V[i].status = UNDEF;
        }
        // вес искомой вершины
        V[n].weight = 0;
        result[n] = 0;
        // очередь вершин
        TreeSet<Vertex> vertQueue = new TreeSet<Vertex>((v1, v2) -> {
            if (v1.weight == v2.weight)
                return Integer.compare(v1.n, v2.n);
            else return v1.weight > v2.weight ? 1 : -1;
        });
        vertQueue.add(V[n]);

        // пока очередь не пуста
        while (vertQueue.size() != 0) {
            Vertex aV = vertQueue.pollFirst(); // вершина с минимальным весом
            // работа по данной вершине завершена
            aV.status = DONE;
            result[aV.n] = aV.weight;
            // пройдем по связям этой вершины
            int[] links = graph[aV.n]; // да, выгоднее было бы что-то типа связного списка - его можно было бы подготовить в конструкторе
            for (int i = 0; i < size; i++) {
                int edgeWeight = links[i];
                if (edgeWeight < INFINITY) {
                    // с вершиной есть связь
                    Vertex bV = V[i];
                    if (bV.status != DONE) {
                        // вершина еще не расчитана до конца
                        int newWeight = edgeWeight + aV.weight;
                        if (newWeight < bV.weight) {
                            // найден меньший вес до вершины
                            if (bV.status == CALC)
                                vertQueue.remove(bV);
                            else
                                bV.status = CALC;
                            bV.weight = newWeight;
                            vertQueue.add(bV); // положим в очередь
                        }
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        final int inf = INFINITY;

        // входная матрица графа, f = бесконечность
        int[][] matrix = {
                {0, 7, 9, inf, inf, 14},
                {7, 0, 10, 15, inf, inf},
                {9, 10, 0, 11, inf, 2},
                {inf, 15, 11, 0, 6, inf},
                {inf, inf, inf, 6, 0, 9},
                {14, inf, 2, inf, 9, 0}};

        // готовим алгоритм
        Dijkstra dijkstra = new Dijkstra(matrix);

        // выводим для нулевого узла
        //System.out.println(Arrays.deepToString(d.find(0)));
        System.out.println(Arrays.toString(dijkstra.find(0)));
        System.out.println(Arrays.toString(dijkstra.find(1)));
        System.out.println(Arrays.toString(dijkstra.find(2)));
        System.out.println(Arrays.toString(dijkstra.find(3)));
        System.out.println(Arrays.toString(dijkstra.find(4)));
        System.out.println(Arrays.toString(dijkstra.find(5)));
    }

}

