package ru.progwards.java2.lessons.less15.abstr;

public enum Bank2Factory implements BankFactory {
    INSTANCE;
    @Override
    public DebetCard createCard() {
        return new Bank2DebetCard();
    }
}

