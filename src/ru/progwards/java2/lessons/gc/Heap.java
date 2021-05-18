package ru.progwards.java2.lessons.gc;

import java.util.*;

import static java.nio.file.Files.size;

public class Heap {

    private byte[] bytes;

    // словарь со свободными блоками: ключ - длина блока, значение - коллекция указателей
    private TreeMap<Integer, Integer> freeBlocks;
    // словарь с занятыми блоками: ключ - указатель, значение - длина блока
    private TreeMap<Integer, Integer> busyBlocks;

    // кэш для хранения данных при проходе по словарю
    private Map.Entry<Integer, Integer> current;

    private int quantityCompact;


    Heap(int maxHeapSize) {
        freeBlocks = new TreeMap<>();
        busyBlocks = new TreeMap<>();
        bytes = new byte[maxHeapSize];
        freeBlocks.put(bytes.length, 0);
        quantityCompact = 0;
    }
    public int malloc(int size) {
        int startIndex = 0;

        // находим, если есть, элемент freeBlocks с ключом больше либо равным size
        Map.Entry<Integer, Collection<Integer>> entry;//.ceilingEntry(size);
        entry = size().ceilingEntry(freeBlocks);


        if (entry != null) {
            // коллекция из указателей
            SortedSet<Integer> set = (SortedSet<Integer>) entry.getValue();
            // startIndex - первый элемент коллекции
            startIndex = set.first();
            // добавили занятый блок в словарь busyBlocks
            busyBlocks.put(startIndex, size);
            // удаляем элемент из свободных блоков
            freeBlocks.remove(entry.getKey(), startIndex);
            int newSize = entry.getKey() - size;
            if (newSize != 0)
                // добавляем новый свободный блок с новыми значениями длины и указателя
                freeBlocks.put(newSize, startIndex + size);
        } else {
            /*
             * если quantityCompact равно 1, то значит мы уже вызывали метод compact()
             * и после этого опять ничего не нашли, значит выдаём исключение
             *  */
            if (quantityCompact == 1)
                throw new OutOfMemoryException(size);
            compact();
            ++quantityCompact;
            malloc(size);
        }
        return startIndex;
    }
    public void free(int ptr) {
        // если в занятых блоках найден блок с указателем ptr
        if (busyBlocks.containsKey(ptr)) {
            // добавляем этот блок в свободные блоки
            freeBlocks.put(busyBlocks.get(ptr), ptr);
            // удаляем блок из структуры занятых блоков
            busyBlocks.remove(ptr);
        } else
            // бросаем исключение, если не нашлось занятого блока с указателем ptr
            throw new InvalidPointerException(ptr);
    }

    public void defrag() {
        // преобразуем TreeMultimap в TreeMap
        TreeMap<Integer, Integer> freeBlocksMap = getMap(freeBlocks);
        freeBlocks.clear();

        for (var block : freeBlocksMap.entrySet()) {
            if (current != null) {
                /*
                 * если сумма ключа(указателя) + значения(дины) равна указателю следующего элемента,
                 * то значит блоки смежные и их можно объединить
                 *  */
                if (current.getKey() + current.getValue() == block.getKey()) {
                    // указатель объединеноого блока
                    int bigPtr = current.getKey();
                    // длина объединеноого блока
                    int bigSize = current.getValue() + block.getValue();
                    // удаляем из TreeMultimap блоки current и block
//                    freeBlocks.remove(current.getValue(), current.getKey());
//                    freeBlocks.remove(block.getValue(), block.getKey());
                    // добавляем объединенный блок
                    freeBlocks.put(bigSize, bigPtr);
                    // меняем значение у current
                    current.setValue(bigSize);
                    continue;
                }
            }
            current = block;
        }
    }

    public void compact() {
        int startIndex = 0;
        TreeMap<Integer, Integer> newBusyBlocks = new TreeMap<>(busyBlocks);
        busyBlocks.clear();
        //  смещаем занятые блоки в начало
        for (var block : newBusyBlocks.entrySet()) {
            busyBlocks.put(startIndex, block.getValue());
            startIndex += block.getValue();
        }

        // если в свободных блоках что-то есть, то создаём TreeMap и очищаем freeBlocks
        if (!freeBlocks.isEmpty()) {
            TreeMap<Integer, Integer> newFreeBlocks = getMap(freeBlocks);
            freeBlocks.clear();

            for (var block : newFreeBlocks.entrySet()) {
                // добавляем свободные блоки после занятых блоков
                freeBlocks.put(block.getValue(), startIndex);
                startIndex += block.getValue();
            }

            // проводим дефрагментацию
            defrag();
        }
    }

    // метод создает из TreeMultimap обычный TreeMap
    private TreeMap<Integer, Integer> getMap(Map<Integer, Integer> map) {
        TreeMap<Integer, Integer> resultMap = new TreeMap<>();

        for (var elem : map.entrySet()) {
            resultMap.put(elem.getValue(), elem.getKey());
        }

        return (resultMap.isEmpty()) ? null : resultMap;
    }
    public static void main(String[] args) {
        Heap heap = new Heap(50);

        System.out.println(heap.freeBlocks);

        heap.malloc(14);
        heap.malloc(7);

        System.out.println(heap.freeBlocks);
        System.out.println(heap.busyBlocks);

        heap.free(0);

        System.out.println(heap.freeBlocks);
        System.out.println(heap.busyBlocks);

        heap.defrag();

        System.out.println(heap.freeBlocks);
        System.out.println(heap.busyBlocks);

        heap.malloc(5);

        System.out.println(heap.freeBlocks);
        System.out.println(heap.busyBlocks);

        heap.malloc(9);

        System.out.println(heap.freeBlocks);
        System.out.println(heap.busyBlocks);

        heap.free(0);

        System.out.println(heap.freeBlocks);
        System.out.println(heap.busyBlocks);

        heap.malloc(20);

        System.out.println(heap.freeBlocks);
        System.out.println(heap.busyBlocks);

        heap.malloc(14);

        System.out.println(heap.freeBlocks);
        System.out.println(heap.busyBlocks);
    }
}


