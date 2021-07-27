package ru.progwards.java2.lessons.less12;

import ru.progwards.java2.lessons.graph.BoruvkaMod;
import ru.progwards.java2.lessons.graph.IBoruvka;

import java.util.List;

import static ru.progwards.java2.lessons.less12.BoruvkaGenerator.genGraph;
import static ru.progwards.java2.lessons.less12.BoruvkaGenerator.simpleGraph;

/**
 * Нагрузочное тестирование классов, замеры времени и памяти
 * В данном случае: алгоритма Борувки
 */
public class BoruvkaTester {

    long startMem;
    long stepMem;
    long spentTime;
    long stepTime;
    long serviceTime;

    private static final long SLEEP_INTERVAL = 100;

    private long getMemoryUse() {
        collectGarbage();
        collectGarbage();
        long totalMemory = Runtime.getRuntime().totalMemory();
        collectGarbage();
        collectGarbage();
        long freeMemory = Runtime.getRuntime().freeMemory();
        return (totalMemory - freeMemory);
    }

    private void collectGarbage() {
        try {
            System.gc();
            Thread.currentThread().sleep(SLEEP_INTERVAL);
            System.runFinalization();
            Thread.currentThread().sleep(SLEEP_INTERVAL);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    void statStart(String name) {
        startMem = getMemoryUse();
        stepMem = startMem;
        spentTime = 0;
        serviceTime = 0;
        System.out.println("\nStatistic " + name + " started.");
        stepTime = System.nanoTime();
    }

    void statStep(String name) {
        long time = System.nanoTime();
        long mem = getMemoryUse();
        long spentStep = time - stepTime;
        spentTime += spentStep;
        String step = name + ": " + " ".repeat(Math.max(7 - name.length(), 0)) + (mem - stepMem) / 1024 + " kb " + spentStep / 1000 + " mcs";
        System.out.println(step + " ".repeat(Math.max(30 - step.length(), 0)) + " Total: " + mem / 1024 + " kb " + spentTime / 1000 + " mcs");
        stepMem = mem;
        stepTime = System.nanoTime();
        serviceTime = stepTime - time;
    }

    void statEnd(String name) {
        System.out.println(name + " (finished), serviceTime " + serviceTime / 1000 + " mcs");
    }

    void test(Class clazz, BoruvkaMod.Graph graph) throws Exception {
        statStart("test(" + clazz.getSimpleName() + ")");
        IBoruvka b = (IBoruvka) clazz.getDeclaredConstructor().newInstance();
        statStep("Created");
        List<BoruvkaMod.Edge<String, String>> result = b.getMinEdgeTree(graph);
        statStep("Done");
        double weight = 0;
        for (BoruvkaMod.Edge<String, String> e : result)
            weight += e.weight;
        statEnd("test(" + clazz.getSimpleName() + ") size=" + result.size() + " weight=" + weight);
    }

    public static void main(String[] args) throws Exception {
        BoruvkaTester t = new BoruvkaTester();
        t.test_one(simpleGraph());
        t.test_one(genGraph(50, 500));
        t.test_one(genGraph(100, 1_000));
        t.test_one(genGraph(1_000, 10_000));
        t.test_one(genGraph(10_000, 100_000));
        //t.test_one(genGraph(100_000, 500_000));
    }

    private void test_one(BoruvkaMod.Graph graph) throws Exception {
        System.out.println("\nGRAPH nodes=" + graph.nodes.size() + " edges=" + graph.edges.size());
        test(BoruvkaDequeue.class, graph);
        test(BoruvkaThreaded.class, graph);
    }

}