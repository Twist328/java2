package ru.progwards.java2.lessons.recursion.less2;

public class EuclidNODStepInfo {  // нахождение НОД Евклида Разбор по шагам
    static int nod(int x, int y) {
        System.out.println("прямой ход, x = " + x + ", y = " + y);
        int result = 0;
        if (x == y)
            result = x;
        else
            result = x > y ? nod(x - y, y) : nod(x, y - x);
        System.out.println("обратный ход, x = " + x + ", y = " + y);
        return result;
    }
    public static void main(String[] args) {
        System.out.println(nod(15, 25));
    }
}

