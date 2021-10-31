package gc;

import com.google.common.collect.*;
import java.util.*;

public class Heap {
    private static byte[] BYTES;//куча

    // словарь со свободными блоками: ключ - длина блока, значение - коллекция указателей
    private static TreeMultimap<Integer, Integer> FREEBLOCKS;
    // словарь с занятыми блоками: ключ - указатель, значение - длина блока
    private static TreeMap<Integer, Integer> FULLBLOCKS;

    // кэш для хранения данных при проходе по словарю
    private Map.Entry<Integer, Integer> CASH;

    private static int TOCOMPACT;

    public Heap(int maxHeapSize) {
        FREEBLOCKS = TreeMultimap.create();
        FULLBLOCKS = new TreeMap<>();
        BYTES = new byte[maxHeapSize];
        FREEBLOCKS.put(BYTES.length, 0);
        TOCOMPACT = 0;
    }

    public Heap() {

    }

    public int malloc(int size) {//Malloc (от англ. memory allocation, выделение памяти)
        // — это функция выделения динамической памяти, входящая в стандартную библиотеку языка Си
        int startIndex = 0;

        // находим, если есть, элемент FREEBLOCKS с ключом больше либо равным size
        Map.Entry<Integer, Collection<Integer>> entry = FREEBLOCKS.asMap().ceilingEntry(size);

        if (entry != null) {
            // коллекция из указателей
            SortedSet<Integer> set = (SortedSet<Integer>) entry.getValue();
            // startIndex - первый элемент коллекции
            startIndex = set.first();
            // добавили занятый блок в словарь occupiedBlocks
            FULLBLOCKS.put(startIndex, size);
            // удаляем элемент из свободных блоков
            FREEBLOCKS.remove(entry.getKey(), startIndex);
            int newSize = entry.getKey() - size;
            if (newSize != 0)
                // добавляем новый свободный блок с новыми значениями длины и указателя
                FREEBLOCKS.put(newSize, startIndex + size);
        } else {
            /*
             * если TOCOMPACT равно 1, то значит мы уже вызывали метод compact()
             * и после этого опять ничего не нашли, значит выдаём исключение
             *  */
            if (TOCOMPACT == 1)
                throw new OutOfMemoryException(size);
            compact();
            ++TOCOMPACT;
            malloc(size);
        }
        return startIndex;
    }

    public void free(int ptr) {
        // если в занятых блоках найден блок с указателем ptr
        if (FULLBLOCKS.containsKey(ptr)) {
            // добавляем этот блок в свободные блоки
            FREEBLOCKS.put(FULLBLOCKS.get(ptr), ptr);
            // удаляем блок из структуры занятых блоков
            FULLBLOCKS.remove(ptr);
        } else
            // бросаем исключение, если не нашлось занятого блока с указателем ptr
            throw new InvalidPointerException(ptr);
    }

    public void defrag() {
        // преобразуем TreeMultimap в TreeMap
        TreeMap<Integer, Integer> freeBlocksMap = getMap(FREEBLOCKS);
        FREEBLOCKS.clear();

        for (var block : freeBlocksMap.entrySet()) {
            if (CASH != null) {
                /*
                 * если сумма ключа(указателя) + значения(длины) равна указателю следующего элемента,
                 * то значит блоки смежные и их можно объединить
                 *  */
                if (CASH.getKey() + CASH.getValue() == block.getKey()) {
                    // указатель объединеноого блока
                    int bigPtr = CASH.getKey();
                    // длина объединеноого блока
                    int bigSize = CASH.getValue() + block.getValue();
                    // удаляем из TreeMultimap блоки current и block
                    FREEBLOCKS.remove(CASH.getValue(), CASH.getKey());
                    FREEBLOCKS.remove(block.getValue(), block.getKey());
                    // добавляем объединенный блок
                    FREEBLOCKS.put(bigSize, bigPtr);
                    // меняем значение у current
                    CASH.setValue(bigSize);
                    continue;
                }
            }
            CASH = block;
        }
    }

    public void compact() {
        int startIndex = 0;
        TreeMap<Integer, Integer> newBusyBlocks = new TreeMap<>(FULLBLOCKS);
        FULLBLOCKS.clear();
        //  смещаем занятые блоки в начало
        for (var block : newBusyBlocks.entrySet()) {
            FULLBLOCKS.put(startIndex, block.getValue());
            startIndex += block.getValue();
        }

        // если в свободных блоках что-то есть, то создаём TreeMap и очищаем freeBlocks
        if (!FREEBLOCKS.isEmpty()) {
            TreeMap<Integer, Integer> newFreeBlocks = getMap(FREEBLOCKS);
            FREEBLOCKS.clear();

            for (var block : newFreeBlocks.entrySet()) {
                // добавляем свободные блоки после занятых блоков
                FREEBLOCKS.put(block.getValue(), startIndex);
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
        System.out.println("\n*********************************************");
        Heap heap = new Heap(110);

        heap.malloc(21);
        heap.malloc(20);
        heap.malloc(13);
        heap.malloc(25);
        heap.malloc(3);
        heap.malloc(2);
        heap.malloc(10);
        heap.malloc(16);

        heap.free(21);
        heap.free(41);
        heap.free(0);
        heap.free(94);

        heap.FULLBLOCKS.forEach((k, v) -> System.out.println("занятый блок: Key = " + k + ", Value = " + v));
        System.out.println("*********************************************");
        heap.FREEBLOCKS.forEach((k, v) -> System.out.println("свободный блок: Key = " + k + ", Value = " + v));
       // heap.defrag();
        heap.compact();
        System.out.println("*********************************************");
        heap.FREEBLOCKS.forEach((k, v) -> System.out.println("свободный блок после compact() : Key = " + k + ", Value = " + v));
        System.out.println("*********************************************");
    }
}

