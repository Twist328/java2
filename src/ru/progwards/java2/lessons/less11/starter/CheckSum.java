package ru.progwards.java2.lessons.less11.starter;

public class CheckSum implements Task {
    @Override
    public String process(byte[] data) {
        if (data.length == 0)
            return "Нет данных";

        byte checkSum= 0;
        for (int i = 0; i < data.length; i++) {
            checkSum += data[i];
        }

        return "Контрольная сумма: " + checkSum;

    }
}


