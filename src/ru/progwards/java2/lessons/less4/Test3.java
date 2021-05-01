package ru.progwards.java2.lessons.less4;


public class Test3 {
    static final int ITERATIONS = 10000000;

    static void testAdd() {
        long t1 = System.currentTimeMillis();
        BIntArray<Integer> ba = new BIntArray<>(5000000, 5000000);
        for(int i=0; i<ITERATIONS; i++)
            ba.add(i);
        long t2 = System.currentTimeMillis();
        OList<Integer> ol = new OList<>();
        for(int i=0; i<ITERATIONS; i++)
            ol.add(i);
        long t3 = System.currentTimeMillis();
        EList el = new EList();
        for(int i=0; i<ITERATIONS; i++)
            el.add(new Test2.MyItem(i));
        long t4 = System.currentTimeMillis();

        System.out.println("--- test add ---");
        System.out.println("BIntArray = "+(t2-t1));
        System.out.println("OList  = "+(t3-t2));
        System.out.println("EList  = "+(t4-t3));

        testGet(ba, ol, el);
    }

    static void testGet(BIntArray<Integer> ba, OList<Integer> ol, EList el) {
        long t1 = System.currentTimeMillis();
        for(int i=0; i<ITERATIONS; i++)
            ba.get(i);
        long t2 = System.currentTimeMillis();
        OList<Integer>.ListItem<Integer> current = ol.getHead();
        while(current != null) {
            current = current.getNext();
        }
        long t3 = System.currentTimeMillis();
        Test2.MyItem current2 = (Test2.MyItem) el.getHead();
        while(current2 != null) {
            current2 = (Test2.MyItem) current2.getNext();
        }
        long t4 = System.currentTimeMillis();

        System.out.println("--- test get ---");
        System.out.println("BIntArray = "+(t2-t1));
        System.out.println("OList  = "+(t3-t2));
        System.out.println("EList  = "+(t4-t3));
    }

    public static void main(String[] args) {
        testAdd();
    }
}

