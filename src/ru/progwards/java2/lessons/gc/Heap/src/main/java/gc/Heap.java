package gc;

import com.google.common.collect.*;
import java.util.*;

public class Heap {
    private byte[] bytes;

    // словарь со свободными блоками: ключ - длина блока, значение - коллекция указателей
    private TreeMultimap<Integer, Integer> looseBlocks;
    // словарь с занятыми блоками: ключ - указатель, значение - длина блока
    private TreeMap<Integer, Integer> occupiedBlocks;

    // кэш для хранения данных при проходе по словарю
    private Map.Entry<Integer, Integer> current;

    private int quantityCompact;

    public Heap(int maxHeapSize) {
        looseBlocks = TreeMultimap.create();
        occupiedBlocks = new TreeMap<>();
        bytes = new byte[maxHeapSize];
        looseBlocks.put(bytes.length, 0);
        quantityCompact = 0;
    }

    public int malloc(int size) {
        int startIndex = 0;

        // находим, если есть, элемент looseBlocks с ключом больше либо равным size
        Map.Entry<Integer, Collection<Integer>> entry = looseBlocks.asMap().ceilingEntry(size);

        if (entry != null) {
            // коллекция из указателей
            SortedSet<Integer> set = (SortedSet<Integer>) entry.getValue();
            // startIndex - первый элемент коллекции
            startIndex = set.first();
            // добавили занятый блок в словарь occupiedBlocks
            occupiedBlocks.put(startIndex, size);
            // удаляем элемент из свободных блоков
            looseBlocks.remove(entry.getKey(), startIndex);
            int newSize = entry.getKey() - size;
            if (newSize != 0)
                // добавляем новый свободный блок с новыми значениями длины и указателя
                looseBlocks.put(newSize, startIndex + size);
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
        if (occupiedBlocks.containsKey(ptr)) {
            // добавляем этот блок в свободные блоки
            looseBlocks.put(occupiedBlocks.get(ptr), ptr);
            // удаляем блок из структуры занятых блоков
            occupiedBlocks.remove(ptr);
        } else
            // бросаем исключение, если не нашлось занятого блока с указателем ptr
            throw new InvalidPointerException(ptr);
    }

    public void defrag() {
        // преобразуем TreeMultimap в TreeMap
        TreeMap<Integer, Integer> freeBlocksMap = getMap(looseBlocks);
        looseBlocks.clear();

        for (var block : freeBlocksMap.entrySet()) {
            if (current != null) {
                /*
                 * если сумма ключа(указателя) + значения(длины) равна указателю следующего элемента,
                 * то значит блоки смежные и их можно объединить
                 *  */
                if (current.getKey() + current.getValue() == block.getKey()) {
                    // указатель объединеноого блока
                    int bigPtr = current.getKey();
                    // длина объединеноого блока
                    int bigSize = current.getValue() + block.getValue();
                    // удаляем из TreeMultimap блоки current и block
                    looseBlocks.remove(current.getValue(), current.getKey());
                    looseBlocks.remove(block.getValue(), block.getKey());
                    // добавляем объединенный блок
                    looseBlocks.put(bigSize, bigPtr);
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
        TreeMap<Integer, Integer> newBusyBlocks = new TreeMap<>(occupiedBlocks);
        occupiedBlocks.clear();
        //  смещаем занятые блоки в начало
        for (var block : newBusyBlocks.entrySet()) {
            occupiedBlocks.put(startIndex, block.getValue());
            startIndex += block.getValue();
        }

        // если в свободных блоках что-то есть, то создаём TreeMap и очищаем freeBlocks
        if (!looseBlocks.isEmpty()) {
            TreeMap<Integer, Integer> newFreeBlocks = getMap(looseBlocks);
            looseBlocks.clear();

            for (var block : newFreeBlocks.entrySet()) {
                // добавляем свободные блоки после занятых блоков
                looseBlocks.put(block.getValue(), startIndex);
                startIndex += block.getValue();
            }

            // проводим дефрагментацию
            defrag();
        }
    }

    // метод создает из TreeMultimap обычный TreeMap
    private TreeMap<Integer, Integer> getMap(TreeMultimap<Integer, Integer> map) {
        TreeMap<Integer, Integer> resultMap = new TreeMap<>();

        for (var elem : map.entries()) {
            resultMap.put(elem.getValue(), elem.getKey());
        }

        return (resultMap.isEmpty()) ? null : resultMap;
    }

    public static void main(String[] args) {
        Heap heap = new Heap(120);

        heap.looseBlocks.forEach((k, v) -> System.out.println("cвободный блок:Key = " + k + ", Value = " + v));
        heap.occupiedBlocks.forEach((k, v) -> System.out.println("занятый блок: Key = " + k + ", Value = " + v));

        heap.malloc(21);
        heap.malloc(20);
        heap.malloc(13);
        heap.malloc(50);

        heap.looseBlocks.forEach((k, v) -> System.out.println("свободный блок: Key = " + k + ", Value = " + v));
        System.out.println("----");
        heap.free(21);
        heap.occupiedBlocks.forEach((k, v) -> System.out.println("занятый блок: Key = " + k + ", Value = " + v));

    }
}

