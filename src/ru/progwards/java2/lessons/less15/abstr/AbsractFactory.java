package ru.progwards.java2.lessons.less15.abstr;

import static ru.progwards.java2.lessons.less15.abstr.Client.productInfo;

public enum AbsractFactory {
    INSTANCE;
    public Product getProduct(String bankId, String productId) {
        // выбор банка (фабрики)
        BankFactory factory = null;
        switch (bankId) {
            case "bank1": factory = Bank1Factory. INSTANCE; break;
            case "bank2": factory = Bank2Factory. INSTANCE; break;
            case "bank3": factory = Bank3Factory. INSTANCE; break;
        }
        if (factory != null) {
            switch (productId) {
                case "card": return factory.createCard();
                case "account": return factory.createAccount();
            }
        }
        return null;
    }
    public static void main(String[] args) {
        // идентификаторы приходят из UI
        System.out.println("\n*********************************");
        productInfo("bank1", "card");
        productInfo("bank1", "account");
        productInfo("bank2", "card");
        productInfo("bank2", "account");
        productInfo("bank3", "card");
        productInfo("bank3", "account");
        System.out.println("********************************");
    }
}

