package ru.progwards.java2.lessons.less15.abstr;

public enum Bank1Factory implements BankFactory {
    INSTANCE;
    @Override
    public DebetCard createCard() {
        return new Bank1DebetCard();
    }
    @Override
    public Account createAccount() {
        return new Bank1Account();
    }
}
