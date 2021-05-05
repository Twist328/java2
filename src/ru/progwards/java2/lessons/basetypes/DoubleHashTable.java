package ru.progwards.java2.lessons.basetypes;


import java.util.*;
import java.util.function.Function;

public class DoubleHashTable  <K extends HashValue,V>
        extends Dictionary<K,V> {


    static class Prime {
        static List<Integer> list;
        private static int check;

        static {
            list = new ArrayList<Integer>();
            list.addAll(Arrays.asList(2, 3, 5, 7, 11));
            check = 12;
        }

        static private void fillArray(int fillTo) {
            for (int num = check + 1; num <= fillTo; num++) {
                for (int i : list) {
                    if (num % i == 0) break;
                    if (i * i > num) {
                        list.add(num);
                        break;
                    }
                }
            }
            check = fillTo;
        }

        static int get(int num) {
            int fillTo = (int) Math.sqrt(num + Math.sqrt(num)); // предел проверки
            fillArray(fillTo);
            boolean found = false;
            while (!found) {
                num++;
                found = true;
                for (int i : list) {
                    if (num % i == 0) {
                        found = i == num;
                        break;
                    }
                }
            }
            return num;
        }
    }


    static class Entry<K extends HashValue, V> {// запись для хранения одного элемента таблицы

        private V value;
        final private K key;
        final private int hash;

        Entry(int hash, K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }
    }
    /*
Реализовать класс DoubleHashTable - хэш таблица с двойным хэшированием
В качестве размера таблицы выбирать простое число, первоначальное значение 101
При количестве коллизий более 10% при одной серии пробирований - перестраивать таблицу, увеличивая размер
Стратегия роста - удвоение размера, но с учетом правила - размер таблицы простое число. Т.е. искать ближайшее простое
Ключи должны реализовывать интерфейс interface HashValue {int getHash();}
Для числовых значение взять хэш 2 функции - деление и умножение
Для строковых - выбрать что-то, из представленных в лекции
     */

    private Object[] item;      // item
    private boolean[] deleted;     // удаленные элементы
    private int itemSize;       // размер item
    private int incPercent;        // на сколько процентов увеличивать при нехватке
    private int collPercent = 10;  // критичный процент коллизий
    private int size;              // количество элементов в таблице
    private int Collplus;         // при каком  значении количества коллизий увеличивать item
    private DoubleHashStart f1; // функция хэширования (принимает хэш, размер item и номер коллизии)

    DoubleHashTable() {
        itemSize = 100;
        incPercent = 100;
        init();
    }

    DoubleHashTable(int itemSize) {
        this.itemSize = itemSize;
        incPercent = 100;
        init();
    }

    DoubleHashTable(int itemSize, int incPercent) {
        this.itemSize = itemSize;
        this.incPercent = incPercent;
        init();
    }

    private void init() {
        itemSize = Prime.get(itemSize);
        item = new Object[itemSize];
        deleted = new boolean[itemSize];
        size = 0;
        Collplus = itemSize * collPercent / 100;
        f1 = (hash, size, collis) -> {

            double d = 0.6180339887d * ((hash + collis * hash) & 0x7FFFFFFF); // золотое сечение =(sqrt(5)-1)/2
            int res = (int) ((d - Math.floor(d)) * size);
            //System.out.println("k="+k+" s="+s+" c="+c+"  result="+res);
            return res;
        };
    }

    public void setCalculateFunction(DoubleHashStart f) {
        if (size == 0) f1 = f;
        else throw new IllegalStateException("Table is not empty");
    }


    public int size() { // получить количество элементов
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Enumeration<K> keys() {
        return this.<K>getEnumeration(KEYS);
    }

    public Enumeration<V> elements() {
        return this.<V>getEnumeration(VALUES);
    }

    public V add(K key, V value) { // по задаче имя метода// добавить пару ключ-значение
        return put(key, value);
    }


    public V get(Object key) { // from IntDictionary<K,V> // получить значение по ключу
        int hash = ((K) key).getHash();
        int collis = 0;
        while (true) {
            if (collis > Collplus) {
                return null;
            }
            int index = f1.apply(hash, itemSize, collis++);
            Entry<K, V> entry = (Entry<K, V>) item[index];
            if (entry == null) {
                if (!deleted[index]) return null;
            } else if ((entry.hash == hash) && entry.key.equals(key)) {
                return entry.value;
            }
        }
    }

    @Override
    public V put(K key, V value) {
        return null;
    }


    public V remove(Object key) {// удалить элемент по ключу
        int hash = ((K) key).getHash();
        int collis = 0;
        while (true) {
            if (collis > Collplus) {
                return null;
            }
            int index = f1.apply(hash, itemSize, collis++);
            Entry<K, V> e = (Entry<K, V>) item[index];
            if (e == null) {
                if (!deleted[index]) {

                    return null;
                }
            } else if ((e.hash == hash) && e.key.equals(key)) {
                item[index] = null;
                deleted[index] = true;
                size--;
                return e.value;
            }
        }
    }


    public V change(K key1, K key2) {// изменить значение ключа у элемента с key1 на key2
        V value = remove(key1);
        put(key2, value);
        return value;
    }

    private <T> Enumeration<T> getEnumeration(int type) {
        if (size == 0) {
            return Collections.emptyEnumeration();
        } else {
            return new Enumerator<>(type, false);
        }
    }

    private <T> Iterator<T> getIterator(int type) {
        if (size == 0) {
            return Collections.emptyIterator();
        } else {
            return new Enumerator<>(type, true);
        }
    }

    public Iterator<Entry<K, V>> getIterator() {
        return getIterator(ENTRIES);
    }

    private static final int KEYS = 0;
    private static final int VALUES = 1;
    private static final int ENTRIES = 2;


    private class Enumerator<T> implements Enumeration<T>, Iterator<T> {
        final Object[] table = DoubleHashTable.this.item;
        final boolean[] deleted = DoubleHashTable.this.deleted;
        int itemIndex = -1;
        int count = 0;
        int allCount = DoubleHashTable.this.size;
        int itemSize = DoubleHashTable.this.itemSize;
        final int type;


        final boolean iterator;

        Enumerator(int type, boolean iterator) {
            this.type = type;
            this.iterator = iterator;
        }

        public boolean hasMoreElements() {
            return count < allCount;
        }

        public T nextElement() {
            for (int i = itemIndex + 1; i < itemSize; i++) {
                Entry<?, ?> entry = (Entry<?, ?>) table[i];
                if (entry != null) {
                    itemIndex = i;
                    count++;
                    return type == KEYS ? (T) entry.key : (type == VALUES ? (T) entry.value : (T) entry);
                }
            }
            throw new NoSuchElementException("Hashtable Enumerator");
        }


        public boolean hasNext() {
            return hasMoreElements();
        }// Iterator methods

        public T next() {
            return nextElement();
        }

        public void remove() {
            if (!iterator) throw new UnsupportedOperationException();
            item[itemIndex] = null;
            deleted[itemIndex] = true;
        }
    }

    public static void main(String[] args) {


        DoubleHashTable<Value, String> table = new DoubleHashTable();

        table.setCalculateFunction((k, s, c) -> {
            double d = 0.6180339887d * ((k + c) & 0x7FFFFFFF); // золотое сечение =(sqrt(5)-1)/2
            int res = (int) ((d - Math.floor(d)) * s);
            //System.out.println("k="+k+" s="+s+" c="+c+"  result="+res);
            return res;
        });

        table.setCalculateFunction((k, s, c) -> {
            double d1 = 0.6180339887d * (k & 0x7FFFFFFF); // золотое сечение =(sqrt(5)-1)/2
            int f1 = (int) ((d1 - Math.floor(d1)) * s);
            int f2 = c * c;
            int res = (f1 + f2) % s;
            //System.out.println("k="+k+" s="+s+" c="+c+"  result="+res);
            return res;
        });

               table.setCalculateFunction((k, m, c) -> {

            double tmp = 0.6180339887d * (k & 0x7FFFFFFF); // золотое сечение =(sqrt(5)-1)/2
            int h1 = (int) ((tmp - Math.floor(tmp)) * m);
            int h2 = k * k;
            int res = (h1 + c * h2) % m;
            //System.out.println("k="+k+" s="+s+" c="+c+"  result="+res);
            return res;
        });

        Function<Integer, Integer> f1 = x -> x * x;
        Function<Integer, Integer> f2 = x -> (int)(618.0339887d*x);

        table.setCalculateFunction((k, m, c) -> {
            int r1 = f1.apply(k);
            int r2 = f2.apply(k);
            int res = (r1 + c * r2) % m;
            //System.out.println("k="+k+" s="+s+" c="+c+"  result="+res);
            return res;
        });

        table.setCalculateFunction((h,s,c)->c);
        for (int i = 200; i < 388; i++) {

            table.put(Value.of(i), "value " + i);
        }
        System.out.println("ItemSize=" + table.itemSize);

        }
    }
//ItemSize=101
//211 elements -> 21 collisions limit
//184 for k+c
//200 for k+c*k*c
//207 for k+c*k

 /*long start = System.nanoTime();
        System.out.println(Prime.get(10000));
        System.out.println(Prime.get(20));
        System.out.println((System.nanoTime()-start)/1000);//1310751-18x
        System.out.println("[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199]");
        System.out.println(Prime.list);*/
 /* Iterator<Entry<Value, String>> i = table.getIterator();
        while (i.hasNext()) {
            Entry<Value, String> e = i.next();
            System.out.println("k="+e.key+" v="+e.value);*/
/* При двойном хешировании используются две независимые хеш-функции h1(k) и h2(k).
             Пусть k — это наш ключ, m — размер нашей таблицы, n mod m — остаток от деления n на m,
             тогда сначала исследуется ячейка с адресом h1(k), если она уже занята, то рассматривается
             (h1(k)+h2(k))mod m, затем (h1(k)+2⋅h2(k))mod m и так далее.
             В общем  идёт проверка последовательности ячеек (h1(k)+i⋅h2(k))modm где i=(0,1,...,m−1)*/
 /*public void rehashWithInc() {
        itemSize = itemSize + itemSize * incPercent / 100;
        rehash();
    }

    public void rehash() {
        DoubleHashTable table = new DoubleHashTable(itemSize, incPercent);
        table.setCalculateFunction(f1);

        for (int i = item.length; i-- > 0; ) {
            Entry<K, V> e = (Entry<K, V>) item[i];
            if (e != null) {
                table.put(e.key, e.value);
            }
        }

        itemSize = table.itemSize;
        item = table.item;
        deleted = table.deleted;
        Collplus = table.Collplus;
    }

    // добавить пару ключ-значение громоздско очень
   /* public V put(K key, V value) { // from IntDictionary<K,V>
        if (value == null) {
            throw new NullPointerException();
        }
        int hash = key.getHash();
        int collis = 0;
        while (true) {
            if (collis > Collplus) {
                rehashWithIncrement();
                collis = 0;
            }
            int index = f1.apply(hash, itemSize, collis++);
            Entry<K, V> e = (Entry<K, V>) item[index];
            if (e == null) {
                item[index] = new Entry<>(hash, key, value);
                deleted[index] = false;
                size++;
                return null;
            } else if ((e.hash == hash) && e.key.equals(key)) {
                V oldValue = e.value;
                e.value = value;
                size++;
                return oldValue;
            }
        }
    }*/
