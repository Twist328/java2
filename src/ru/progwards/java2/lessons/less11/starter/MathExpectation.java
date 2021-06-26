package ru.progwards.java2.lessons.less11.starter;

public class MathExpectation implements Task {
    @Override
    public String process(byte[] data) {
        if (data.length == 0)
            return "Нет данных";
        long sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        double mathExpectation = (double) sum / data.length;

        return "Мат ожидание: " + mathExpectation;
    }
}