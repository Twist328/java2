package ru.progwards.java2.lessons.less2;

public class AsNumbersSum {

/**
 * @program: java2
 * @description:
 * @autor: twist328
 * @create: 2021-09-14 10
 **/

//    public static String asNumbersSum(int number) {
//        if (number == 1)
//            return  "1";
//        int res = number - 1;
//        int res2 = number - res;
//        return number + " = " + asNumbersSum(res);
//    }

    public static String asNumbersSum(int number) {
        return number
                + divideIntoTerms(number-1, 1, "");
    }

    public static String divideIntoTerms(int number, int secondTerm, String lastTerm) {
        if (number <= 0){
            return "";
        }else
        if (secondTerm > number)
            return divideIntoTerms(number, secondTerm - number
                    , lastTerm + number + "+")
                    + divideIntoTerms(number - 1, secondTerm + 1, lastTerm);
        else return " = " + lastTerm + number + "+"
                + secondTerm
                + divideIntoTerms(secondTerm - 1, 1,
                lastTerm + number + "+")
                + divideIntoTerms(number - 1, secondTerm + 1, lastTerm);

    }

    // пример использования рекурсии вместо цикла
    static void rec(int i, int count) {
        if (i >= count)
            return;
        System.out.println(i);
        rec(++i, count);
    }

    public static void main(String[] args) {
        System.out.println(asNumbersSum(15));

//        rec(0, 5);
    }}