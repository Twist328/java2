package ru.progwards.java2.lessons.less14;



import java.util.*;

public class Heap {
    private byte[] bytes;

    // словарь со свободными блоками: ключ - размер блока, значение - коллекция указателей
    private TreeMap<Integer, SortedSet<Integer>> freeBlocks;

    // словарь с занятыми блоками: ключ - указатель, значение - размер блока
    private TreeMap<Integer, Integer> busyBlocks;

    // кэш для хранения данных при проходе по словарю
    private Map.Entry<Integer, Integer> current;

    private int quantityCompact;

    Heap(int maxHeapSize) {
        freeBlocks = new TreeMap<>();
        busyBlocks = new TreeMap<>();
        bytes = new byte[maxHeapSize];
        freeBlocks.put(bytes.length, new TreeSet<>());
        freeBlocks.get(bytes.length).add(0);
        quantityCompact = 0;
    }

    public int malloc(int size) {
        int startIndex = 0;

        // находим, если есть, элемент freeBlocks с ключом больше либо равным size
        Map.Entry<Integer, SortedSet<Integer>> entry = freeBlocks.ceilingEntry(size);

        if (entry != null) {
            // коллекция из указателей
            SortedSet<Integer> set = entry.getValue();
            // startIndex - первый элемент коллекции
            startIndex = set.first();
            // добавили занятый блок в словарь busyBlocks
            busyBlocks.put(startIndex, size);
            // удаляем элемент из свободных блоков
            if (set.size() > 1) {
                set.remove(startIndex);
                freeBlocks.put(entry.getKey(), set);
            } else
                freeBlocks.remove(entry.getKey());

            int newSize = entry.getKey() - size;
            if (newSize != 0)
                // добавляем новый свободный блок с новыми значениями размера и указателя
                addFreeBlocks(newSize, startIndex + size);
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
            // размер свободного блока
            int size = busyBlocks.get(ptr);
            // добавляем этот блок в свободные блоки
            addFreeBlocks(size, ptr);
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
                    addFreeBlocks(bigSize, bigPtr);
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
                addFreeBlocks(block.getValue(), startIndex);
                startIndex += block.getValue();
            }

            // проводим дефрагментацию
            defrag();
        }
    }

    private TreeMap<Integer, Integer> getMap(TreeMap<Integer, SortedSet<Integer>> map) {
        TreeMap<Integer, Integer> resultMap = new TreeMap<>();

        for (var elem : map.entrySet()) {
            for (Integer ptr : elem.getValue()) {
                resultMap.put(ptr, elem.getKey());
            }
        }

        return resultMap;
    }

    // метод добавляет в freeBlocks ключ key и значение value
    private void addFreeBlocks(int key, int value) {
        SortedSet<Integer> s = freeBlocks.get(key);
        if (s != null)
            s.add(value);
        else {
            freeBlocks.put(key, new TreeSet<>());
            freeBlocks.get(key).add(value);
        }
    }

    public static void main(String[] args) {

        TreeMap<Integer, SortedSet<Integer>> map = new TreeMap<>();
        SortedSet<Integer> set = new TreeSet<>(Set.of(2, 7, -1, 0));
        SortedSet<Integer> set2 = new TreeSet<>(Set.of(3, 17, -10, 1000));
        map.put(3, set);
        map.put(7, set2);
        System.out.println(map);
        SortedSet<Integer> s = map.get(3);
        if (s != null)
            s.add(77);
        System.out.println(map);

//        System.out.println(getMap2(map));
    }
}