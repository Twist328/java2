package ru.progwards.java2.lessons.synchro.gc;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
При тесте в часто возникает ошибка высвобождения какого-то объекта(пока не понял)
*/

public class Heap implements HeapInterface {

    enum BlockType {EMPTY, OBJECT};

    class MBlock {
        int ptr;
        int size;
        BlockType type;
        MBlock prev;
        MBlock next;

        public MBlock(int ptr, int size, BlockType type, MBlock prev, MBlock next) {
            this.ptr = ptr;
            this.size = size;
            this.type = type;
            this.prev = prev;
            this.next = next;
        }
    }

    byte[] memory;
    Hashtable<Integer, MBlock> objectsMapByPtr; // список объектов по адресам, ключ = адрес
    TreeMap<Integer, ArrayDeque<MBlock>> emptiesTreeBySize; // поиск пустых блоков по размеру
    //Hashtable<Integer, MBlock> emptiesMapByPtr; // список пустых по адресу
    final int averageObjectSize = 64; // средний размер объекта (для рассчета общего количества)

    MBlock firstObject = null; // первый занятый не дефрагменченный блок

    int freeSize;

    final HeapService heapService = new HeapService(this);
    final Thread thread = new Thread(heapService);
    final Lock lock = new ReentrantLock();

    Heap(int maxHeapSize) {
        memory = new byte[maxHeapSize];
        freeSize = maxHeapSize;
        int expectedObjectsCount = maxHeapSize / averageObjectSize;
        int expectedEmptiesCount = expectedObjectsCount / 10;

        objectsMapByPtr = new Hashtable<>(expectedObjectsCount);
        emptiesTreeBySize = new TreeMap<>();
        //emptiesMapByPtr = new Hashtable<>(expectedEmptiesCount);

        MBlock emptyBlock = new MBlock(0, maxHeapSize, BlockType.EMPTY, null, null);
        newEmpty(emptyBlock);
        //firstEmpty = emptyBlock;

        thread.start();
    }

    public void dispose() {
        lock.lock();
        try {
            heapService.interruptMe();
            memory = null;
            objectsMapByPtr = null;
            emptiesTreeBySize = null;
            //emptiesMapByPtr = null;
        } finally {
            lock.unlock();
        }
    }

    public int malloc(int size) throws OutOfMemoryException {
        lock.lock();
        try {
            Map.Entry<Integer, ArrayDeque<MBlock>> found;

            found = emptiesTreeBySize.ceilingEntry(size);
            if (found == null) {

                found = emptiesTreeBySize.ceilingEntry(size);
                if (found == null) {
                    Map.Entry<Integer, ArrayDeque<MBlock>> f = emptiesTreeBySize.pollFirstEntry();
                    ArrayDeque<MBlock> a = f.getValue();
                    MBlock b = a.getFirst();
                    System.out.print("emptiesTreeBySize:" + emptiesTreeBySize.size());
                    System.out.print(" ArrayDeque:" + a.size());
                    System.out.print(" MBlock: ptr=" + b.ptr + " size=" + b.size);
//                    throw new OutOfMemoryException("Cannot malloc " + size + " bytes of memory.");
                }

            }

           int foundSize = found.getKey();
            freeSize -= size;
            if (foundSize == size) {
                MBlock eBlock = pollEmpty(found.getValue());
                eBlock.type = BlockType.OBJECT;
                newObject(eBlock);
                return eBlock.ptr;
            } else {
                MBlock eBlock = shrinkEmpty(found.getValue(), size);
                MBlock newObject = new MBlock(eBlock.ptr - size, size, BlockType.OBJECT, eBlock.prev, eBlock);
                if (eBlock.prev != null)
                    eBlock.prev.next = newObject;
                eBlock.prev = newObject;
                newObject(newObject);
                return newObject.ptr;
            }
        } finally {
            lock.unlock();
        }
    }

    private MBlock shrinkEmpty(ArrayDeque<MBlock> empties, int takeSize) {
        MBlock block = empties.pollFirst();
        if (empties.size() == 0) {
            emptiesTreeBySize.remove(block.size);
        }
        //emptiesMapByPtr.remove(block.ptr);

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
        //emptiesMapByPtr.put(block.ptr, block);

        return block;
    }


    private void newEmpty(MBlock block) {

        ArrayDeque<MBlock> newArray = emptiesTreeBySize.get(block.size);

        if (newArray != null) {
            //newArray.add(block);
            newArray.addFirst(block);
        } else {
            newArray = new ArrayDeque<>();
            newArray.add(block);
            emptiesTreeBySize.put(block.size, newArray);
        }

    }


    private MBlock pollEmpty(ArrayDeque<MBlock> empties) {
        MBlock block = empties.poll();

        if (empties.size() == 0) {
            emptiesTreeBySize.remove(block.size);
        }
        //emptiesMapByPtr.remove(block.ptr);

        return block;
    }

    private void newObject(MBlock block) {
        objectsMapByPtr.put(block.ptr, block);

        if (firstObject == null)
            firstObject = block;
        else if (firstObject.ptr > block.ptr) {
            synchronized (firstObject) {
                if (firstObject == null || firstObject.ptr > block.ptr)
                    firstObject = block;
            }
        }
    }

    public void free(int ptr) throws InvalidPointerException {
        lock.lock();
        try {
            MBlock block = objectsMapByPtr.remove(ptr);
            if (block == null) {
                System.out.println("free PTR=" + ptr);
                throw new InvalidPointerException();
            }
            block.type = BlockType.EMPTY;
            freeSize += block.size;
            newEmpty(block);
        } finally {
            lock.unlock();
        }
    }


    private void resizeEmpty(MBlock block, MBlock prevBlock) {
        ArrayDeque<MBlock> empties = emptiesTreeBySize.get(block.size);
        if (empties.size() == 1) {
            emptiesTreeBySize.remove(block.size);
        } else {
            empties.remove(block);
        }

        block.size += prevBlock.size;
        ArrayDeque<MBlock> newArray = emptiesTreeBySize.get(block.size);

        if (newArray != null) {
            newArray.add(block);
        } else {
            newArray = new ArrayDeque<>();
            newArray.add(block);
            emptiesTreeBySize.put(block.size, newArray);
        }
        prevBlock.prev.next = block;
        block.prev = prevBlock.prev;

    }

    public void defrag() throws OutOfMemoryException {
        lock.lock();
        try {
            System.out.print("Defrag(" + emptiesTreeBySize.size() + ")...");

        } finally {
            lock.unlock();
        }
    }

    public void compact() throws OutOfMemoryException {
        System.out.print("Compact(" + emptiesTreeBySize.size() + ")");
        lock.lock();
        heapService.interruptMe();
        try {
            System.out.print("(" + emptiesTreeBySize.size() + ")...");

            //if (emptiesMapByPtr.size() == 1) return;//throw new OutOfMemoryException();

            MBlock prevBlock;
            MBlock block = firstObject;
            boolean emptyFound = false;

            while (block != null && block.type != BlockType.OBJECT)
                block = block.prev;
            if (block == null) {
                block = new MBlock(firstObject.ptr, 0, BlockType.OBJECT, firstObject.prev, firstObject.next);
            }

            while (true) {
                prevBlock = block;
                while (true) {
                    block = block.next;
                    if (block == null || block.type == BlockType.OBJECT)
                        break;
                    else if (!emptyFound) emptyFound = true;
                }
                if (block == null) break;
                if (emptyFound) {
                    moveObject(block, prevBlock.ptr + prevBlock.size);
                    prevBlock.next = block;
                    block.prev = prevBlock;
                }
            }
            firstObject = prevBlock;
            int lastByte = prevBlock.ptr + prevBlock.size;

            System.out.print(" lastByte=" + lastByte);

            emptiesTreeBySize = new TreeMap<>();
            //emptiesMapByPtr = new Hashtable<>();
            int size = memory.length - lastByte;
            MBlock emptyBlock = new MBlock(lastByte, size, BlockType.EMPTY, null, null);
            newEmpty(emptyBlock);
            //firstEmpty = emptyBlock;
            System.out.println(" done(" + emptiesTreeBySize.size() + ")");
        } finally {
            lock.unlock();
        }
    }

    private void moveObject(MBlock block, int ptr) {
        HeapTest.getBytes(block.ptr, null);
        HeapTest.setBytes(ptr, null);
        objectsMapByPtr.remove(block.ptr);
        block.ptr = ptr;
        objectsMapByPtr.put(block.ptr, block);
    }


    private void removeEmpty(MBlock block) {
        ArrayDeque<MBlock> empties = emptiesTreeBySize.get(block.size);

        if(empties == null)
            return;
        if (empties.size() == 1) {
            emptiesTreeBySize.remove(block.size);
        } else {
            empties.remove(block);
        }
        //emptiesMapByPtr.remove(block.ptr);
    }

    class HeapService implements Runnable {
        Heap heap;
        Thread thisThread;
        boolean myInterrupted = false;
        Lock lock;
        final boolean log = false;

        public HeapService(Heap heap) {
            this.heap = heap;
        }

        @Override
        public void run() {
            this.lock = heap.lock;
            try {
                while (!myInterrupted) {
                    Thread.sleep(100);

                    limit = memory.length - freeSize - freeSize / 10 - memory.length / 10;

                    //defragToLimit();
                }
            } catch (InterruptedException e) {
            }
            if(log)System.out.println("HeapService interrupted. myInterrupted=" + myInterrupted);
        }

        int limit;

        private void defragToLimit() throws InterruptedException {
            if(log)System.out.println("HeapService. defragToLimit. limit = "+limit);
            while (!myInterrupted) {
                //System.out.println("HeapService. defragToLimit. firstObject="+firstObject.ptr);
                MBlock saveFirstObject = firstObject;
                if(saveFirstObject==null)
                    break;

                MBlock emptyBlock = defragFromFirstObject(firstObject);

                // заведем пустой блок
                if (emptyBlock.size > 0) {
                    if (lock.tryLock(100, TimeUnit.MILLISECONDS))
                        try {
                            emptyBlock.next.prev = emptyBlock;
                            emptyBlock.prev.next = emptyBlock;
                            newEmpty(emptyBlock);
                        } finally {
                            lock.unlock();
                        }
                    else
                        break;
                }
                // передвинем указатель на первый объект
                if (saveFirstObject == firstObject)
                    synchronized (firstObject) {
                        if (saveFirstObject == firstObject) {
                            firstObject = emptyBlock.prev;
                        }
                    }
                if (emptyBlock.ptr > limit) break;
            }
            if(!myInterrupted)
                if(log)
                    System.out.println("HeapService. defragToLimit. Compacted.");
                else
                    System.out.print("*");
        }

        int freed;
        int i;
        MBlock obj;
        MBlock prevObj;

        private MBlock defragFromFirstObject(MBlock saveFirstObject) throws InterruptedException {
            freed = 0;
            i = 0;
            obj = saveFirstObject;
            while (!myInterrupted && obj.ptr < limit) {
                prevObj = obj;

                boolean isFound = findBlockToCompact(); // modified: prevObj, obj, freed

                if (!isFound)
                    break;

                if (lock.tryLock(100, TimeUnit.MILLISECONDS))
                    try {
                        if (obj.prev.next == obj && obj.type == BlockType.OBJECT) {
                            moveObject(obj, prevObj.ptr + prevObj.size);
                            prevObj.next = obj;
                            obj.prev = prevObj;
                        }
                    } finally {
                        lock.unlock();
                    }
                else
                    break;
                if (i % 100 == 0) {
                    synchronized (firstObject) {
                        if (saveFirstObject == firstObject) {
                            firstObject = obj;
                            saveFirstObject = firstObject;
                        } else
                            break;
                    }
                } else {
                    if (saveFirstObject != firstObject)
                        break;
                }
                if (myInterrupted) break;
            }
            synchronized (firstObject) {
                if (saveFirstObject == firstObject)
                    firstObject = obj;
            }
            return new MBlock(obj.ptr + obj.size, freed, BlockType.EMPTY, obj, obj.next);
        }

        private boolean findBlockToCompact() throws InterruptedException {
            boolean emptyFound = false;
            while (!myInterrupted && obj.ptr < limit) {
                obj = obj.next;
                //if(obj.next == prevObj || obj.next == obj) break;
                if(obj == null)
                    return false;
                if (emptyFound) {
                    if (obj.type == BlockType.OBJECT)
                        return true;
                    else {
                        if(!lockedRemoveEmpty(obj))
                            return false;
                        freed += obj.size;
                    }
                } else {
                    if (obj.type == BlockType.OBJECT)
                        prevObj = obj;
                    else {
                        if(!lockedRemoveEmpty(obj))
                            return false;
                        emptyFound = true;
                        freed+= obj.size;
                    }
                }
            }
            return false;
        }

        private boolean lockedRemoveEmpty(MBlock block) throws InterruptedException {
            if (lock.tryLock(100, TimeUnit.MILLISECONDS))
                try {
                    if (block.type == BlockType.EMPTY) {
                        removeEmpty(block);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            return false;
        }

        public void interruptMe() {
            myInterrupted = true;
            while (thread.isAlive()) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
