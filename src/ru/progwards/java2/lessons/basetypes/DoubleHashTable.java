package ru.progwards.java2.lessons.basetypes;

import java.util.*;

public class DoubleHashTable<K extends HashValue,V> {
    private static final double A = 0.6180339887;
    private static final int N = 13;

    static class Node<K extends HashValue,V> {
        private final K key;
        private V value;
        private boolean isDeleted;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            isDeleted = false;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        private void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private static final long INT_MAX = 4294967295L;

    private Node[] table;

    DoubleHashTable() {
        table = new Node[101];
    }

    public void add(K key, V value) {
        int index = getHash(key);
        Node<K, V> node = new Node<>(key, value);
        /*
         * если элемент с индексом index = null или ключ элемента соответствует переданному ключу, то перезаписываем
         * этот элемент
         * */
        if (table[index] == null || table[index].getKey().equals(key)) {
            table[index] = node;
            return;
        }
        // вычисляем шаг, с которым будем идти по массиву
        int n = getHashStep(key);
        // считаем колличество коллизий (начально значение 1, т.к. мы уже не положили элемент в таблицу с первого раза)
        int count = 1;
        // пока элемент с индексом index + n занят другим значением - продолжаем поиск, прибавляя n
        // проходимся по массиву делая полный круг и если не коллизии превысили 10%, то увеличиваем массив
        for (int i = index + n; ; i += n) {
            int indx = i % table.length;
            if (table[indx] == null || table[indx].getKey().equals(key)) {
                table[indx] = node;
                break;
            }
            ++count;
            // если колличество коллизий больше 10%, то увеличиваем размер таблицы
            if (count > (table.length / 10)) {
                increaseArrayLenght();
                add(key, value);
                break;
            }
        }
    }

    public V get(K key) {
        int index = getHash(key);
        if (table[index] == null)
            return null;
        // если ключ элемента соответствует переданному ключу и элемент не удалённый, то возвращаем этот элемент
        if (table[index].getKey().equals(key) && !table[index].isDeleted())
            return (V) table[index].getValue();
        int n = getHashStep(key);
        // счетчик коллизий как в методе add
        int count = 1;
        for (int i = index + n; ; i += n) {
            int indx = i % table.length;
            if (table[indx] != null && table[indx].getKey().equals(key) && !table[indx].isDeleted()) {
                return (V)table[indx].getValue();
            }
            if (table[indx] == null)
                return null;
            ++count;
            // если колличество коллизий больше 10%, то значит такого элемента в таблице не существует
            if (count > (table.length / 10)) {
                return null;
            }
        }
    }

    // убрать дублирование кода

    public void remove(K key) {
        int index = getHash(key);
        if (table[index] == null)
            return;
        // если нашли элемент по ключу и он не удалён, то удаляем его
        if (table[index].getKey().equals(key) && !table[index].isDeleted()) {
            table[index].setDeleted(true);
            return;
        }
        int n = getHashStep(key);
        int count = 1;
        for (int i = index + n; ; i += n) {
            int indx = i % table.length;
            if (table[indx] != null && table[indx].getKey().equals(key) && !table[indx].isDeleted()) {
                table[indx].setDeleted(true);
                break;
            }
            if (table[indx] == null)
                return;
            ++count;
            // если колличество коллизий больше 10%, то значит такого элемента в таблице не существует
            if (count > (table.length / 10)) {
                return;
            }
        }
    }

    public void change(K key1, K key2) {
        V value = get(key1);
        if (value == null)
            return;
        // если элемент с ключом key1 существуют, то удаляем его и добавляем элемент с ключом key2 и значением value
        remove(key1);
        add(key2, value);
    }

    public int size() {
        int result = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isDeleted())
                ++result;
        }
        return result;
    }

    public Iterator<Node<K,V>> getIterator() {
        return new Iterator<>() {
            private int index = 0;
            private Node<K,V> lastReturned;
            private Node<K,V> current = table[index];

            @Override
            public boolean hasNext() {
                /*
                 * проверка на current == null или current удалён выполняется всего 1 раз, когда indx = 0, чтобы сразу
                 * найти индекс следующего элемента != null и не удаленного
                 * также эта проверка нужна, чтобы метод next() вернул false в случае если в таблице все элементы = null
                 * или все элементы удалены
                 * */
                if (current == null || current.isDeleted()) {
                    if (findNextNotNull())
                        current = table[index];
                    else
                        return false;
                }
                return index < table.length;
            }

            @Override
            public Node<K, V> next() {
                if (!hasNext())
                    throw new NullPointerException();

                lastReturned = current;
                /*
                 * смотрим на элемент вперёд и если он = null или удалён, то проверяем есть ли вообще элементы != null
                 * если элементы есть, то присваиваем переменной indx значение индекса первого встретившегося элемента
                 * если элементов != null нет, то выводим переменную index за границы таблицы, чтобы метод hasNext()
                 * вернул false
                 * */
                int indxNext = index + 1;
                if (indxNext < table.length) {
                    if (table[indxNext] == null || table[indxNext].isDeleted) {
                        if (findNextNotNull())
                            current = table[index];
                        else
                            index = table.length;
                    } else {
                        ++index;
                        current = table[index];
                    }
                } else
                    ++index;

                return lastReturned;
            }

            // метод выясняет имеются ли дальше в таблице элементы != null
            private boolean findNextNotNull() {
                for (int i = index + 1; i < table.length; i++) {
                    /*
                     * если елемент имеется и он не удален, то присваиваем переменной indx значение индекса этого
                     * элемента
                     *  */
                    if (table[i] != null && !table[i].isDeleted()) {
                        index = i;
                        return true;
                    }
                }
                return false;
            }
        };
    }

    // метод преобразует ключ в индекс массива
    private int getHash(K key) {
        return key.getHash() % table.length;
    }

    /*
     * метод преобразует ключ в значение шага, с которым будем проходить по массиву, пока не найдём значение элемента
     * равное null
     *  */
    private int getHashStep(K key) {
        double d = A * key.getHash();
        return (int)(N * (d % 1));
    }

    // метод определяет простое число или нет
    private boolean isSimple(int num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }

    // метод находит простое число > num
    private int getSimpleUp(int num) {
        for (int i = 1; i < num; i++) {
            int value = num + i;
            if (isSimple(value)) {
                if (i == 1)
                    return 1;
                return value;
            }
        }
        return 0;
    }

    // метод находит простое число < num
    private int getSimpleDown(int num) {
        for (int i = 1; i < num; i++) {
            int value = num - i;
            if (isSimple(value)) {
                if (i == 1)
                    return 1;
                return value;
            }
        }
        return 0;
    }

    // метод ищет ближайшее простое число к num или выводит num, если оно простое
    private int searchNearSimpleNum(int num) {
        if (isSimple(num))
            return num;

        int resultUp = getSimpleUp(num);
        if (resultUp == 1)
            return num + resultUp;
        int resultDown = getSimpleDown(num);
        if (resultDown == 1)
            return num - resultDown;

        // сравниваем близость до num - какое число ближе то и возвращаем
        if ((resultUp - num) < (num - resultDown))
            return resultUp;
        if ((resultUp - num) > (num - resultDown))
            return resultDown;
        return resultUp;
    }

    // метод увеличивает массив в два раза, но с учётом того, что длина нового массива должна быть простым числом
    private void increaseArrayLenght() {
        // получаем список из элементов таблицы table
        List<Node<K,V>> listNode = putOnAList();
        // получаем увеличенную длину таблицы, которая является простым числом
        int lenght = searchNearSimpleNum(table.length * 2);
        Node[] newTable = new Node[lenght];
        table = newTable;
        // записываем в новую таблицу элементы из списка listNode с новым hash (получаем новый индекс)
        changeHash(listNode);
    }

    // метод записывает в список элементы таблицы table
    private List<Node<K,V>> putOnAList() {
        List<Node<K,V>> result = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null)
                result.add(table[i]);
        }
        return result;
    }

    // метод вычисляет новый hash для каждого элемента списка list и записывает эти элементы в новую таблицу
    private void changeHash(List<Node<K,V>> list) {
        list.forEach(x -> {
            int index = getHash(x.getKey());
            table[index] = x;
        });
    }
}

