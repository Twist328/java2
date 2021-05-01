package ru.progwards.java2.lessons.less4;

public class Test4l2 {
    static int hash(int k) {       //Хэш-функция основанная на умножении
        int N = 13;  //N - размер таблицы
        Double A= 0.6180339887;// начение золотого сечения 1/φ=(√5-1)/2≈0,6180339887
        double d = A * k;           //ключ
        return (int) (N * (d - Math.floor(d)));

    }

    public static void main(String[] args) {

        System.out.println(hash(25));
        System.out.println(hash(26));
        System.out.println(hash(27));
    }
}
