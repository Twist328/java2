package ru.progwards.java2.lessons.synchro.gc;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Многопоточная версия с сервисным компактизирующим потоком
В блоках добавлены ссылки на предыдущие и следующие блоки, которые постоянно актуализируются, убран emptiesTreeBySize
Не требуется сортировка перед дефргментацией и компактизацией.
Дефрагментация закомментирована, т.к. сервисный поток её выполняет успешно, а в очередь свободных блоков свободные попадают
в начало - как бы оставляя старые сервисному потоку в удаление Heap_initial_hashTable работает также, суда по passed time,
но общее время malloc time и free time у него значительно меньше
При тесте в 25% случаев возникает ошибка высвобождения не существующего объекта - сложно найти, не охота думать :)
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

    //TreeMap<Integer, MBlock> objectsTreeByPtr; // список объектов по адресам, ключ = адрес
    //TreeMap<Integer, MBlock> emptiesTreeByPtr; // поиск пустых блоков по размеру

    //MBlock firstEmpty; // первый свободный блок
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

                //defrag();

                //found = emptiesTreeBySize.ceilingEntry(size);
                //if (found == null) {

                //compact();

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
                //}
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
        // можно было бы объединять пустые при высвобождении, но пусть это будет в сервисном потоке
        // если не объединяем, тогда надо бы включить дефрагментацию. Не будем, чтобы не поддерживать firstEmpty
        /*MBlock prev = block.prev;
        while(prev!=null && prev.type == BlockType.EMPTY) {
            ArrayDeque<MBlock> empties = emptiesTreeBySize.get(prev.size);
            if (empties.size() == 1) {
                emptiesTreeBySize.remove(prev.size);
            } else {
                empties.remove(prev);
            }
            block.size += prev.size;
            block.ptr = prev.ptr;
            emptiesMapByPtr.remove(prev.ptr);
            prev = prev.prev;
            block.prev = prev;
            if(prev!=null) prev.next = block;
        }
        MBlock next = block.next;
        while(next!=null && next.type == BlockType.EMPTY) {
            ArrayDeque<MBlock> empties = emptiesTreeBySize.get(next.size);
            if (empties.size() == 1) {
                emptiesTreeBySize.remove(next.size);
            } else {
                empties.remove(next);
            }
            block.size += next.size;
            emptiesMapByPtr.remove(next.ptr);
            next = next.next;
            block.next = next;
            if(next!=null) next.prev = block;
        }*/
        ArrayDeque<MBlock> newArray = emptiesTreeBySize.get(block.size);

        if (newArray != null) {
            //newArray.add(block);
            newArray.addFirst(block); // в итоге - быстрее на 10%, чем add
        } else {
            newArray = new ArrayDeque<>();
            newArray.add(block);
            emptiesTreeBySize.put(block.size, newArray);
        }

        //emptiesMapByPtr.put(block.ptr, block);

//        if(firstEmpty.ptr>block.ptr)
//            synchronized (firstEmpty) {
//                if(firstEmpty.ptr>block.ptr)
//                    firstEmpty = block;
//            }
    }


    private MBlock pollEmpty(ArrayDeque<MBlock> empties) {
        MBlock block = empties.poll();

        if (empties.size() == 0) {
            emptiesTreeBySize.remove(block.size);
        }
        //emptiesMapByPtr.remove(block.ptr);

        return block;
    }

/*    private void removeEmpty(MBlock block) {
        ArrayDeque<MBlock> empties = emptiesTreeBySize.get(block.size);
        empties.remove(block);
        if (empties.size() == 0) {
            emptiesTreeBySize.remove(block.size);
        }
        emptiesMapByPtr.remove(block.ptr);
    }*/


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


/*    private MBlock pollObject(int ptr) throws InvalidPointerException {
        MBlock block = objectsMapByPtr.remove(ptr);
        if (block == null) throw new InvalidPointerException();
        return block;
    }*/

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

//        if(prevBlock==firstEmpty)
//            synchronized (firstEmpty) {
//                if(prevBlock==firstEmpty)
//                    firstEmpty = block;
//            }
    }

    public void defrag() throws OutOfMemoryException {
        lock.lock();
        try {
            System.out.print("Defrag(" + emptiesTreeBySize.size() + ")...");

//        MBlock prevBlock;
//        MBlock block = firstEmpty;
//        if(block==null) throw new OutOfMemoryException();
//
//        while(true) {
//            while(true) {
//                prevBlock = block;
//                block = prevBlock.next;
//                if(block==null || (block.type==BlockType.EMPTY && prevBlock.type==BlockType.EMPTY))
//                    break;
//            }
//            if(block==null)
//                break;
//            resizeEmpty(block, prevBlock);
//        }
//        System.out.println(" done(" + emptiesTreeBySize.size() + ")");
        } finally {
            lock.unlock();
        }
    }

    public void compact() throws OutOfMemoryException {
        System.out.print("Compact(" + emptiesTreeBySize.size() + ")");
//        thread.interrupt();
//        while (thread.isAlive()) {
//            try {
//                Thread.sleep(0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
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

                    /*if(obj == null || obj.ptr > limit) {
                        System.out.println("HeapService. Break while: "+(obj == null?"on Null": obj.ptr+" > "+limit));
                        break;
                    }*/ // перешли на isFound
                    /*if (++i == 10_000) {
                        System.out.println("Moved: " + obj.ptr + " -> " + (prevObj.ptr + prevObj.size));
                        i = 0;
                    }*/
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
