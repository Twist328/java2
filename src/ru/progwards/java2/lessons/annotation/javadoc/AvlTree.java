package ru.progwards.java2.lessons.annotation.javadoc;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/*
Реализовать класс AvlTree - АВЛ дерево, с методами:
        2.1 public void put(KK key, VV value) - добавить пару ключ-значение, если уже такой ключ есть - заменить
        2.2 public void delete(KK key) - удалить ключ
        2.3 public VV get(KK key) - найти ключ
        2.4 public void change(KK oldKey, KK newKey) - заменить значение ключа на другое
        2.5 public void process(Consumer<Entry<KK,VV>> consumer) - прямой обход дерева*/

/**
 * Класс АВЛ дерево (вид бинарного дерева), с методами, а также , {@link Entry}
 * @author Twist
 * @param <K>
 * @param <V>
 * @version 1.0
 * see Entry
 */

public class AvlTree<K extends Comparable<K>, V> implements Map<K,V> {
    /**
     * Переопределенное свойство множества с типом словаря, содержащего ключи, возвращающий дефолтное значение (имплементация интерфейса)
     * @return
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    /**
     * Имплементируемое переопределение возвращающее свойства родителя
     * @param key
     * @param defaultValue
     * @return
     */
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return Map.super.getOrDefault(key, defaultValue);
    }


    private static final String KEYEXIST = "Key already exist";

    private static final String KEYNOTEXIST = "Key not exist";

    /**
     * Вложенный класс Entry с параметрами
     * @param <KK>
     * @param <VV>
     */
    class Entry<KK extends Comparable<KK>, VV> {
        KK key;
        VV value;
        Entry parent;
        Entry left;
        Entry right;
        int height;  // высота
        //int balance; // баланс

        /**
         * метод получить ключ
         * @return
         */
        public KK getKey() {
            return key;
        }

        /**
         * получить значение
         * @return
         */
        public VV getValue() {
            return value;
        }

        /**
         * конструктор вложенного класса
         * @param key
         * @param value
         */
        public Entry(KK key, VV value) {
            this.key = key;
            this.value = value;
            height = 1;
        }

        /**
         * метод сравнения для поиска нужного ключа по определенному значению
         * @param key
         * @return
         */
        private Entry<KK, VV> find(KK key) {
            int cmp = key.compareTo(this.key);
            if (cmp > 0)
                if (right != null)
                    return right.find(key);
                else
                    return this;
            if (cmp < 0)
                if (left != null)
                    return left.find(key);
                else
                    return this;
            return this;
        }

        /**
         * прибавить ключ - значение
         * @param leaf
         * @throws TreeException
         */
        void add(Entry<KK, VV> leaf) throws TreeException {
            int cmp = leaf.key.compareTo(key);
            if (cmp == 0)
                throw new TreeException(KEYEXIST);
            if (cmp > 0) {
                right = leaf;
                leaf.parent = this;
            } else {
                left = leaf;
                leaf.parent = this;
            }
            fixheight(this);
        }

        /**
         * удалить ненужный ключ
         */
        void delete() {
            Entry node = null;
            if (left != null || right != null) {
                if (bfactor() > 0) {
                    node = left.findMaximum();
                } else {
                    node = right.findMinimum();
                }
                node.right = right;
                node.left = left;
                Entry par = node.parent;
                if (par.left == node) {
                    par.left = null;
                } else if (par.right == node) {
                    par.right = null;
                }
                par.parent = parent;
            }
            if (parent.left == this) {
                parent.left = node;
            } else if (parent.right == this) {
                parent.right = node;
            }
            balance(node);
        }

        /**
         * найти ключ с максимальным значением
         * @return
         */
        private Entry<KK, VV> findMaximum() {
            Entry<KK, VV> node = this;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        /**
         * найти ключ с минимальным значением
         * @return
         */
        private Entry<KK, VV> findMinimum() {
            Entry<KK, VV> node = this;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        /**
         * порядок возвращения результатов поиска
         * @return
         */
        @Override
        public String toString() {
            //return "(" + key + "," + value + ")";
            return key.toString() + " h=" + height + " b=" + bfactor();
        }

        /**
         * Вывод строки результатов с параметрами
         * @param prefix
         * @return
         */
        public String toStringAll(String prefix) {
            return this.toString()
                    + (right == null ? "" : "\n" + prefix + "R: " + right.toStringAll(prefix + "⁞  "))
                    + (left == null ? "" : "\n" + prefix + "L: " + left.toStringAll(prefix + "⁞  "));
        }

        /**
         * Обход дерева
         * @param consumer
         */
        public void process(Consumer<Entry<KK, VV>> consumer) {
            if (left != null)
                left.process(consumer);
            consumer.accept(this);
            if (right != null)
                right.process(consumer);
        }

        /**
         * параметр вычисления баланса высот
         * @return
         */
        int bfactor() {
            return height(right) - height(left);
        }

        /**
         * корректировка баланса по высоте
         * @param leaf
         */
        void fixheight(Entry<KK, VV> leaf) {
            leaf.fixheight();
        }

        /**
         * дефолтный метод балансировки
         */
        void fixheight() {
            //leaf.height = Math.max(leaf.recalcHeight(leaf.left), leaf.recalcHeight(leaf.right)) + 1;
            int h1 = height(this.left);
            int h2 = height(this.right);
            this.height = (h1 > h2 ? h1 : h2) + 1;
            //System.out.println("\nfixheight("+this.key+") h="+this.height);
            //System.out.println(root.toStringAll(""));
        }

    }


    protected Entry<K, V> root;

    /**
     * получить ключ по полученному значению
     * @param key
     * @return
     */
    public V get(K key) {
        Entry<K, V> p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp < 0)
                p = p.left;
            else if (cmp > 0)
                p = p.right;
            else
                return p.value;
        }
        return null;
    }

    /**
     * вставка ключа k в дерево с корнем p
     * @param p
     * @param k
     * @return
     */
    Entry<K, V> insert(Entry<K, V> p, Entry<K, V> k) // вставка ключа k в дерево с корнем p
    {
        if (p == null) return k;
        if (k.key.compareTo(p.key) < 0)
            p.left = insert(p.left, k);
        else
            p.right = insert(p.right, k);
        return balance(p);
    }

    /**
     * добавить пару ключ-значение
     * @param p
     * @return
     */
    public Entry<K, V> put(Entry<K, V> p) {
        if (root == null)
            root = p;
        else
            root = insert(root, p);
        return p;
    }

    /**
     * балансировка дерева
     * @param leaf
     * @return
     */
    private Entry<K, V> balance(Entry<K, V> leaf) {
        leaf.fixheight();
        int bf = leaf.bfactor();
        //System.out.println("\nbalance(" + leaf.key + ") bf=" + bf);
        if (bf == 2) {
            if (leaf.right.bfactor() < 0)
                leaf.right = rotateright(leaf.right);
            return rotateleft(leaf);
        } else if (bf == -2) {
            if (leaf.left.bfactor() > 0)
                leaf.left = rotateleft(leaf.left);
            return rotateright(leaf);
        }
        return leaf;
    }

    /**
     * поиск узла с минимальным ключом в дереве p
     * @param p
     * @return
     */
    Entry<K, V> findmin(Entry<K, V> p) // поиск узла с минимальным ключом в дереве p
    {
        return p.left != null ? findmin(p.left) : p;
    }

    /**
     * удаление узла с минимальным ключом из дерева p
     * @param p
     * @return
     */
    Entry<K, V> removemin(Entry<K, V> p) // удаление узла с минимальным ключом из дерева p
    {
        if (p.left == null)
            return p.right;
        p.left = removemin(p.left);
        return balance(p);
    }

    /**
     * переопределения методов вычисления  размера дерева, проверки на пустые узлы, проверки на содержание ключа
     * проверки на содержание определенного значения
     * получения ключа, прибавления ключа-значения
     * замены ключа на ключ с новым значением
     * добавления значений в хэш таблицу(словарь)
     * удаление, замены
     * @return
     */
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return get((K)key);
    }

    public V put(K key, V value) {
        put(new Entry<>(key, value));
        return value;
    }

    @Override
    public V remove(Object key) {
        try {
            Entry<K,V> e = remove(root, (K)key);
            return e.value;
        } catch (TreeException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    /**
     * метод балансировки дерева
     * @param leaf
     * @return
     */
    int height(Entry<K, V> leaf) {
        return leaf == null ? 0 : leaf.height;
    }

    /**
     * порядок удаления ключа с описанием исключения
     * @param p
     * @param k
     * @return
     * @throws TreeException
     */
    Entry<K, V> remove(Entry<K, V> p, K k) throws TreeException {
        if (root == null)
            throw new TreeException(KEYNOTEXIST);

        if (k.compareTo(p.key) < 0)
            p.left = remove(p.left, k);
        else if (k.compareTo(p.key) > 0)
            p.right = remove(p.right, k);
        else {
            Entry<K, V> q = p.left;
            Entry<K, V> r = p.right;
            //delete p;
            if (r == null) return q;
            Entry<K, V> min = findmin(r);
            min.right = removemin(r);
            min.left = q;
            return balance(min);
        }
        return balance(p);
    }

    /**
     * заменя старого ключа на новый с описанием исключеня
     * @param oldKey
     * @param newKey
     * @throws TreeException
     */
    public void change(K oldKey, K newKey) throws TreeException {
        Entry<K, V> current = remove(root, oldKey);
        current.key = newKey;
        put(current);
    }

    /**
     * прямой обход дерева
     * @param consumer
     */
    public void process(Consumer<Entry<K, V>> consumer) {
        if (root != null)
            root.process(consumer);
    }

    /**
     * описание правого вращения р
     * @param p
     * @return
     */
    private Entry<K, V> rotateright(Entry<K, V> p) // правый поворот вокруг p
    {
        Entry<K, V> q = p.left;
        p.left = q.right;
        q.right = p;
        p.fixheight();
        q.fixheight();
        return q;
    }

    /**
     * описание левого вращения q
     * @param q
     * @return
     */
    private Entry<K, V> rotateleft(Entry<K, V> q) // левый поворот вокруг q
    {
        Entry<K, V> p = q.right;
        q.right = p.left;
        p.left = q;
        q.fixheight();
        p.fixheight();
        return p;
    }

    /**
     * тест
     * @param args
     * @throws TreeException
     */
    public static void main(String[] args) throws TreeException {
        AvlTree<Integer, String> t = new AvlTree();
        t.put(45, "");
        t.put(4, "");
        t.put(5, "");
        t.put(9, "");
        t.put(8, "");
        t.put(6, "");
        t.put(7, "");
        t.put(56, "");
        t.put(50, "");
        t.put(3, "");
        t.put(10, "");
        t.put(60, "");
        t.remove(56,"");
        t.put(0,"");

        System.out.println("\n"+ t.root.getKey().equals(8));
        System.out.println("____________________");
        System.out.println(t.root);
        System.out.println("____________________");
        System.out.println(t.root.toStringAll(""));
        System.out.println("____________________");
        System.out.println(t.root.findMinimum());
        System.out.println("____________________");
        System.out.println(t.root.findMaximum());
    }
}

