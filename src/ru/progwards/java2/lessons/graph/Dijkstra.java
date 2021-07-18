package ru.progwards.java2.lessons.graph;

import java.util.*;

public class Dijkstra {
    private int[][] graph;

    Dijkstra(int[][] graph) {
        this.graph = graph;
    }

    public int[]find(int n) {

        if (n > graph.length)
            throw new VertexOutOfBoundsException("Вершина " + n + " находится за пределами graph");

        int[] result1 = new int[graph.length];

        int[][] result = new int[graph.length - 1][graph.length];

        PriorityQueue<Vertex> vertexes = new PriorityQueue<>();
        fillQueue(vertexes, n);

        while (!vertexes.isEmpty()) {
            if (vertexes.size() == 1)
                break;

            Vertex v = vertexes.poll();

            // по этому массиву мы определяем смежные вершины для вершины v

            int[] array = graph[v.getNumber()-1];

            for (int i = 0; i < array.length; i++) {
                if (array[i] != 0) {
                    int numVertex = i+1;
                    int markEdge = array[i];

                    Iterator<Vertex> iterator = vertexes.iterator();
                    while (iterator.hasNext()) {
                        Vertex vertex = iterator.next();
                        if (vertex.getNumber() == numVertex) {
                            int sum = markEdge + v.getMark();
                            if (sum < vertex.getMark())
                                vertex.setMark(sum);
                            result1[i] = vertex.getMark();
                            vertexes.remove(vertex);
                            vertexes.offer(vertex);
                            break;
                        }
                    }
                }
            }
        }
        return result1 ;

    }

    private void fillQueue(Queue<Vertex> queue, int n) {
        for (int i = 1; i <= graph.length; i++) {
            if (i == n)
                continue;
            queue.offer(new Vertex(i, Integer.MAX_VALUE));
        }
        queue.offer(new Vertex(n, 0));
    }


    public static void main(String[] args) {
        int[][] matrix = {{0,3,7,0,0}, {1,0,2,12,5}, {2,5,0,0,3}, {0,15,0,0,9}, {0,7,3,8,0}};
        Dijkstra dijkstra = new Dijkstra(matrix);

        System.out.println("\n***********************");
        System.out.println(Arrays.toString(dijkstra.find(1)));
        System.out.println(Arrays.toString(dijkstra.find(2)));
        System.out.println(Arrays.toString(dijkstra.find(3)));
        System.out.println(Arrays.toString(dijkstra.find(4)));
        System.out.println(Arrays.toString(dijkstra.find(5)));
        System.out.println("***********************");
    }

}

