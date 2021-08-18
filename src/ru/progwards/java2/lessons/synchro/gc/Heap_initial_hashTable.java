package ru.progwards.java2.lessons.synchro.gc;

import java.util.*;

/*
Достаточно оптимальная версия для многопоточного использования, без доп.потоков
emptiesMapByPtr можно было бы убрать, если взять авто-дефрагментацию из Heap методе newEmpty -
не буду тратить время, но вероятно, эффективность Кучи повысится
*/

public class Heap_initial_hashTable implements HeapInterface {

    class MBlock {
        int ptr;
        int size;

        public MBlock(int ptr, int size) {
            this.ptr = ptr;
            this.size = size;
        }
    }

    byte[] memory;
    Hashtable<Integer, MBlock> objectsMapByPtr; // список объектов по адресам, ключ = адрес
    TreeMap<Integer, ArrayDeque<MBlock>> emptiesTreeBySize; // поиск пустых блоков по размеру
    Hashtable<Integer, MBlock> emptiesMapByPtr; // список пустых по адресу
    final int averageObjectSize = 64; // средний размер объекта (для рассчета общего количества)

    Heap_initial_hashTable(int maxHeapSize) {
        memory = new byte[maxHeapSize];
        int expectedObjectsCount = maxHeapSize / averageObjectSize;
        int expectedEmptiesCount = expectedObjectsCount / 10;

        objectsMapByPtr = new Hashtable<>(expectedObjectsCount);
        emptiesTreeBySize = new TreeMap<>();
        emptiesMapByPtr = new Hashtable<>(expectedEmptiesCount);

        MBlock emptyBlock = new MBlock(0, maxHeapSize);
        ArrayDeque<MBlock> emptyBlockArray = new ArrayDeque<>();
        emptyBlockArray.add(emptyBlock);
        emptiesTreeBySize.put(maxHeapSize, emptyBlockArray);
        emptiesMapByPtr.put(0, emptyBlock);
    }

    @Override
    public void dispose() {
    }

    public synchronized int malloc(int size) throws OutOfMemoryException {
        Map.Entry<Integer, ArrayDeque<MBlock>> found;

        found = emptiesTreeBySize.ceilingEntry(size);
        if (found == null) {

            defrag();

            found = emptiesTreeBySize.ceilingEntry(size);
            if (found == null) {

                compact();

                found = emptiesTreeBySize.ceilingEntry(size);
                if (found == null) throw new OutOfMemoryException("Cannot malloc " + size + " bytes of memory.");
            }
        }

        int foundSize = found.getKey();
        if (foundSize == size) {
            MBlock foundEmpty = pollEmpty(found.getValue());
            MBlock newObject = newObject(foundEmpty.ptr, size);
            return newObject.ptr;
        } else {
            int ptr = shrinkEmpty(found.getValue(), size);
            MBlock newObject = newObject(ptr, size);
            return ptr;
        }
    }

    private int shrinkEmpty(ArrayDeque<MBlock> empties, int takeSize) {
        MBlock block = empties.pollFirst();
        int result = block.ptr;
        if (empties.size() == 0) {
            emptiesTreeBySize.remove(block.size);
        }
        emptiesMapByPtr.remove(block.ptr);

        block.size -= takeSize;
        block.ptr += takeSize;
        ArrayDeque<MBlock> newArray = emptiesTreeBySize.get(block.size);

        if (newArray != null) {
            newArray.add(block);
        } else {
            newArray = new ArrayDeque<>();
            newArray.add(block);
            emptiesTreeBySize.put(block.size, newArray);
        }
        emptiesMapByPtr.put(block.ptr, block);

        return result;
    }

    private void resizeEmpty(MBlock block, int addSize) {
        ArrayDeque<MBlock> empties = emptiesTreeBySize.get(block.size);
        if (empties.size() == 1) {
            emptiesTreeBySize.remove(block.size);
        } else {
            empties.remove(block);
        }

        block.size += addSize;
        ArrayDeque<MBlock> newArray = emptiesTreeBySize.get(block.size);

        if (newArray != null) {
            newArray.add(block);
        } else {
            newArray = new ArrayDeque<>();
            newArray.add(block);
            emptiesTreeBySize.put(block.size, newArray);
        }
    }

    private MBlock newEmpty(int ptr, int size) {
        MBlock block = new MBlock(ptr, size);
        ArrayDeque<MBlock> newArray = emptiesTreeBySize.get(size);

        if (newArray != null) {
            newArray.add(block);
        } else {
            newArray = new ArrayDeque<>();
            newArray.add(block);
            emptiesTreeBySize.put(size, newArray);
        }

        emptiesMapByPtr.put(ptr, block);

        return block;
    }


    private MBlock pollEmpty(ArrayDeque<MBlock> empties) {
        MBlock block = empties.poll();

        if (empties.size() == 0) {
            emptiesTreeBySize.remove(block.size);
        }
        emptiesMapByPtr.remove(block.ptr);

        return block;
    }

    private void removeEmpty(MBlock block) {
        ArrayDeque<MBlock> empties = emptiesTreeBySize.get(block.size);
        empties.remove(block);
        if (empties.size() == 0) {
            emptiesTreeBySize.remove(block.size);
        }
        emptiesMapByPtr.remove(block.ptr);
    }


    private MBlock newObject(int ptr, int size) {
        MBlock newObject = new MBlock(ptr, size);

        objectsMapByPtr.put(ptr, newObject);

        return newObject;
    }


    private MBlock pollObject(int ptr) throws InvalidPointerException {
        MBlock block = objectsMapByPtr.remove(ptr);
        if (block == null) throw new InvalidPointerException();

        return block;
    }


    public synchronized void free(int ptr) throws InvalidPointerException {
        MBlock block = pollObject(ptr);
        MBlock newEmpty = newEmpty(block.ptr, block.size);
    }

    public synchronized void defrag() throws OutOfMemoryException {
        System.out.print("Defrag(" + emptiesMapByPtr.size() + ")...");

        Object[] sorted = emptiesMapByPtr.values().toArray();
        Arrays.sort(sorted, Comparator.comparingInt(b -> ((MBlock) b).ptr));
        if (sorted.length <= 1) throw new OutOfMemoryException();

        MBlock prevBlock = (MBlock) sorted[0];
        int len = sorted.length;

        for (int i = 1; i < len; i++) {
            MBlock block = (MBlock) sorted[i];
            if (block.size > 0) {
                if (block.ptr == prevBlock.ptr + prevBlock.size) {
                    removeEmpty(prevBlock);
                    resizeEmpty(block, prevBlock.size);
                }
                prevBlock = block;
            }
        }
        System.out.println(" done(" + emptiesMapByPtr.size() + ")");
    }

    public synchronized void compact() throws OutOfMemoryException {
        System.out.print("Compact(" + emptiesMapByPtr.size() + ")...");

        Object[] sorted = objectsMapByPtr.values().toArray();
        Arrays.sort(sorted, Comparator.comparingInt(b -> ((MBlock) b).ptr));
        if (sorted.length == 0) throw new OutOfMemoryException();

        MBlock prevBlock = (MBlock) sorted[0];
        int len = sorted.length;

        for (int i = 1; i < len; i++) {
            MBlock block = (MBlock) sorted[i];
            int freePtr = prevBlock.ptr+prevBlock.size;
            if (freePtr < block.ptr) {
                moveObject(block, freePtr);
            }
            prevBlock = block;
        }
        int lastByte = prevBlock.ptr+prevBlock.size;

        System.out.print(" lastByte=" + lastByte);

        emptiesTreeBySize = new TreeMap<>();
        emptiesMapByPtr = new Hashtable<>();
        int size = memory.length - lastByte;
        MBlock block = new MBlock(lastByte, size);
        ArrayDeque<MBlock> newArray = new ArrayDeque<>();
        newArray.add(block);
        emptiesTreeBySize.put(size, newArray);
        emptiesMapByPtr.put(lastByte, block);
        System.out.println(" done(" + emptiesMapByPtr.size() + ")");
    }

    private void moveObject(MBlock block, int ptr) {
        HeapTest.getBytes(block.ptr, null);
        HeapTest.setBytes(ptr, null);
        objectsMapByPtr.remove(block.ptr);
        block.ptr = ptr;
        objectsMapByPtr.put(block.ptr, block);
    }
}