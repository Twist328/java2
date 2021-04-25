package ru.progwards.java2.lessons.less2;

public class EuclidNOD {  //Нахождение НОД алгоритмом Евклида
    static int nod(int x, int y) {

        if (x == y)
            return x;// условие выхода
        return x > y ? nod(x - y, y) : nod(x, y - x);
    }
    public static void main(String[] args) {
        System.out.println(nod(15, 25));
        System.out.println(nod(55, 121));
        System.out.println(nod(1024, 4096));
        System.out.println(nod(555, 111));
        System.out.println(nod(54321, 12345));
    }
}

