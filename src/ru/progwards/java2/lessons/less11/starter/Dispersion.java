package ru.progwards.java2.lessons.less11.starter;

public class Dispersion implements Task {
    @Override
    public String process(byte[] data) {
        if (data.length == 0)
            return "Нет данных";

        // мат ожидание
        long sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        double mathExpectation = (double)sum / data.length;

        // дисперсия
        sum = 0;
        for (int i = 0; i < data.length; i++) {
            double diff = mathExpectation - data[i];
            sum += diff * diff;
        }
        double dispersion = (double)sum / data.length;

        return "Дисперсия: " + dispersion + ", среднекв.отклонение: " + Math.sqrt(dispersion);
    }
}
