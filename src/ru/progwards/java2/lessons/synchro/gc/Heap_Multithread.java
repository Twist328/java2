package ru.progwards.java2.lessons.synchro.gc;


import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Heap_Multithread {

    class MBlock {
        int ptr;
        int size;

        public MBlock(int ptr, int size) {
            this.ptr = ptr;
            this.size = size;
        }
    }

    byte[] memory;
    HashMap<Integer, MBlock> objectsMapByPtr; // список объектов по адресам, ключ = адрес
    PriorityQueue<MBlock> objectsQueueByPtr; // список объектов сортированный по адресам
    TreeMap<Integer, ArrayDeque<MBlock>> emptiesTreeBySize; // поиск пустых блоков по размеру
    PriorityQueue<MBlock> emptiesQueueByPtr; // список пустых по адресу
    final int averageObjectSize = 64; // средний размер объекта (для рассчета общего количества)
    final ThreadPoolExecutor executors = new ThreadPoolExecutor(20,50,0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    Heap_Multithread(int maxHeapSize) {
        memory = new byte[maxHeapSize];
        int expectedObjectsCount = maxHeapSize / averageObjectSize;
        int expectedEmptiesCount = expectedObjectsCount / 10;

        objectsMapByPtr = new HashMap<>(expectedObjectsCount);
        objectsQueueByPtr = new PriorityQueue<>(expectedObjectsCount, Comparator.comparingInt(b -> -b.ptr));
        emptiesTreeBySize = new TreeMap<>();
        emptiesQueueByPtr = new PriorityQueue<>(expectedEmptiesCount, Comparator.comparingInt(b -> b.size));

        MBlock emptyBlock = new MBlock(0, maxHeapSize);
        ArrayDeque<MBlock> emptyBlockArray = new ArrayDeque<>();
        emptyBlockArray.add(emptyBlock);
        emptiesTreeBySize.put(maxHeapSize, emptyBlockArray);
        emptiesQueueByPtr.add(emptyBlock);
    }

    public int malloc(int size) throws OutOfMemoryException {
        Map.Entry<Integer, ArrayDeque<MBlock>> found;

        found = emptiesTreeBySize.ceilingEntry(size);
        if (found == null) {

            defrag();

            found = emptiesTreeBySize.ceilingEntry(size);
            if (found == null) {

                compact();

                found = emptiesTreeBySize.ceilingEntry(size);
                if (found == null) throw new OutOfMemoryException("Cannot malloc "+size+" bytes of memory.");
            }
        }

        MBlock foundEmpty = pollEmpty(found.getValue());
        MBlock newEmpty = newEmpty(foundEmpty.ptr + size, foundEmpty.size - size);
        MBlock newObject = newObject(foundEmpty.ptr, size);

        return newObject.ptr;
    }


    private MBlock newEmpty(int ptr, int size) {
        MBlock block = new MBlock(ptr, size);
        ArrayDeque<MBlock> newArray = emptiesTreeBySize.get(block.size);

        if (newArray != null) {
            newArray.add(block);
        } else {
            newArray = new ArrayDeque<>();
            newArray.add(block);
            emptiesTreeBySize.put(size, newArray);
        }
        //emptiesMapByPtr.add(block);
        //main.synchro.gc.Heap.newEmpty(int,int)
        //total:2073 self:1951 execsCount:2129190

//        lockEmptiesQueueByPtr.lock();
//        try {
//            emptiesMapByPtr.add(block);
//        } finally {
//            lockEmptiesQueueByPtr.unlock();
//        }
        //total:111868 self:111868 execsCount:2662887,

        executors.submit(new AddEmptiesQueueByPtr(block));
        //total:1733 self:1733 execsCount:2658879,

        return block;
    }


    private MBlock pollEmpty(ArrayDeque<MBlock> empties) {
        MBlock block = empties.poll();

        if (empties.size() == 0) {
            emptiesTreeBySize.remove(block.size);
        }
        //emptiesMapByPtr.remove(block);
        //ru.progwards.java2.lessons.synchro.gc.Heap.pollEmpty(java.util.ArrayDeque)
        //total:155487 self:155225 execsCount:1773079
        executors.submit(new RemoveEmptiesQueueByPtr(block));
        //total:838 self:838 execsCount:2222346

        return block;
    }

    Lock lockEmptiesQueueByPtr = new ReentrantLock();

    class RemoveEmptiesQueueByPtr implements Runnable {
        MBlock block;

        @Override
        public void run() {
            lockEmptiesQueueByPtr.lock();
            try {
                emptiesQueueByPtr.remove(block);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lockEmptiesQueueByPtr.unlock();
            }
            //Thread.currentThread().interrupt();
        }
        public RemoveEmptiesQueueByPtr(MBlock block) {
            this.block = block;
        }
    }

    class AddEmptiesQueueByPtr implements Runnable {
        MBlock block;

        @Override
        public void run() {
            lockEmptiesQueueByPtr.lock();
            try {
                emptiesQueueByPtr.add(block);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lockEmptiesQueueByPtr.unlock();
            }
            //Thread.currentThread().interrupt();
        }
        public AddEmptiesQueueByPtr(MBlock block) {
            this.block = block;
        }
    }

    private void removeEmpty(MBlock block) {
        ArrayDeque<MBlock> empties = emptiesTreeBySize.get(block.size);
        empties.remove(block);
        if (empties.size() == 0) {
            emptiesTreeBySize.remove(block.size);
        }
        lockEmptiesQueueByPtr.lock();
        try {
            emptiesQueueByPtr.remove(block);
        } finally {
            lockEmptiesQueueByPtr.unlock();
        }
    }


    private MBlock newObject(int ptr, int size) {
        MBlock newObject = new MBlock(ptr, size);

        objectsMapByPtr.put(ptr, newObject);
        objectsQueueByPtr.add(newObject);

        return newObject;
    }


    private MBlock pollObject(int ptr) throws InvalidPointerException {

        MBlock block = objectsMapByPtr.remove(ptr);
        if (block == null) throw new InvalidPointerException();
        //objectsQueueByPtr.remove(block); // надо обнулять размер после pollObject
        //ru.progwards.java2.lessons.synchro.gc.Heap.pollObject(int)
        //total:57096 self:56911 execsCount:354990, -> total:69 self:69 execsCount:354990,

        return block;
    }


    public void free(int ptr) throws InvalidPointerException {
        MBlock block = pollObject(ptr);
        MBlock newEmpty = newEmpty(block.ptr, block.size);
        block.size = 0;
        //TODO: добавить newEmpty в очередь для анализа соседей в фоне (не будет эффекта в нашем тесте, т.к. высвобождение редкое)
    }

    public void defrag() throws OutOfMemoryException {
        System.out.print("Defrag...");
        while (true) {
            int queue = executors.getQueue().size();
            System.out.println(queue);
            if (queue == 0) break;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lockEmptiesQueueByPtr.lock();
        try {
            System.out.println(executors.getQueue().size());
            Iterator<MBlock> iterator = emptiesQueueByPtr.iterator();
            if (!iterator.hasNext()) throw new OutOfMemoryException();
            MBlock prevBlock = iterator.next();

            while (iterator.hasNext()) {
                MBlock block = iterator.next();
                if (block.size > 0 && block.ptr == prevBlock.ptr + prevBlock.size) {
                    removeEmpty(prevBlock);
                    removeEmpty(block);
                    prevBlock = newEmpty(prevBlock.ptr, prevBlock.size + block.size);
                } else {
                    prevBlock = block;
                }
            }
            System.out.println(" done.");
        } finally {
            lockEmptiesQueueByPtr.unlock();
        }
    }

    public void compact() throws OutOfMemoryException {
        System.out.print("Compact...");
        while (true) {
            int queue = executors.getQueue().size();
            System.out.println(executors.getQueue().size());
            if(queue==0) break;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Iterator<MBlock> iterator = objectsQueueByPtr.iterator(); //descending order
        if (!iterator.hasNext()) throw new OutOfMemoryException();
        MBlock prevBlock = iterator.next();

        while (iterator.hasNext()) {
            MBlock block = iterator.next();
            if (block.size > 0 && prevBlock.ptr > block.ptr + block.size) {
                moveObject(block, prevBlock.ptr - block.size);
            }
            prevBlock = block;
        }
        System.out.println(" done.");
    }

    private void moveObject(MBlock block, int ptr) {
        HeapTest.getBytes(block.ptr, null);
        HeapTest.setBytes(ptr, null);
        objectsMapByPtr.remove(block.ptr);
        block.ptr = ptr;
        objectsMapByPtr.put(block.ptr, block);
        //objectsQueueByPtr менять не требуется, т.к. позиция внутри дерева не поменяется
    }
}