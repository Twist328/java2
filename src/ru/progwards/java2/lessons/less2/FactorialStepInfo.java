package ru.progwards.java2.lessons.less2;

public class FactorialStepInfo {  //Разбор факториала по шагам
    public static int factorial(int val){
        System.out.println("Прямой ход, val = " + val);
        int result = val <= 1 ? 1 : factorial(val - 1) * val;
        System.out.println("Обратный ход, val = " + val + ", result = " + result);
        return result;
    }
    public static void main(String[] args) {
        System.out.println(power(2, 20));// возведение в степень (код ниже)
        factorial(5);//факториал числа 5 (код выше)
    }
    static double power(double val, int pow) { //возведение в степень
        switch (pow) {
            case 0: return 1;
            case 1: return val;
            default: {
                double val_halfPower = power(val, pow / 2);
                if (pow % 2 == 1) {
                    return val * val_halfPower * val_halfPower;
                } else {
                    return val_halfPower * val_halfPower;

                }

            }

        }

    }

}

