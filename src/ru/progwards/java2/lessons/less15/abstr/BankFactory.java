package ru.progwards.java2.lessons.less15.abstr;

public interface BankFactory {
    // создать карту
    DebetCard createCard();
    // открыть счёт
    default Account createAccount() {
        return null;
    };
}