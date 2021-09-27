package ru.progwards.java2.lessons.less2;


import ru.progwards.java2.lessons.generics.ArraySort;

import java.util.Locale;

/**
 * @program: java2
 * @description:
 * @autor: twist328
 * @create: 2021-09-27 12
 **/
public class AsNumbersSum2 {
    public static String asNumbersSum(int number) {
        return numbersSum(number, number,"");
    }

    private static String numbersSum(int usedNum, int sum, String prefix) {
        if (sum == 0) return prefix;
        int d;
        if (usedNum < sum) {
            d = usedNum;
        } else d = sum;
        String s = numbersSum(d,sum - d, (prefix.isBlank())? ""+ d : prefix +"+"+ d);
        if (d > 1) {
            s = s +" = "+ numbersSum(d - 1, sum, prefix);
        }
        return s;
    }

    public static void main(String[] args) {
        System.out.println(asNumbersSum(3));
        Recursion(5, "");
    }

    static String Recursion(int n, String str) {
        System.out.println(str + n);
        for (int i=1; i<=n/2; i++){
            Recursion(n-i, str + i + "+");

        }

        return "";
    }
    }
