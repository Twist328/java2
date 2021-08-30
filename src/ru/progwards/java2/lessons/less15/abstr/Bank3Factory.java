package ru.progwards.java2.lessons.less15.abstr;

public enum Bank3Factory implements BankFactory {
    INSTANCE;
    @Override
    public DebetCard createCard() {
        return new Bank3DebetCard();
    }
}
