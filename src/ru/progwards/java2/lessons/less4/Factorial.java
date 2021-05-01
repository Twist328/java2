package ru.progwards.java2.lessons.less4;

public class Factorial {
    static int factorial(int n) {
        System.out.println("factorial");
        if (n == 1)
            return n;
        else
            return n * factorial(n-1);
    }

    public static void main(String[] args) {
        //System.out.println(Factorial.factorial(3));
        System.out.println(246%11);
    }
}
