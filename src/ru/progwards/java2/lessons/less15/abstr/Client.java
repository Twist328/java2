package ru.progwards.java2.lessons.less15.abstr;

public class Client {
    public static void productInfo(String bankId, String productId) {
        Product product = AbsractFactory.INSTANCE.getProduct(bankId, productId);
        if (product == null) {
            System.out.printf("Продукт (%s, %s) не найден\n\n", bankId, productId);
        } else {
            if (product instanceof DebetCard) {
                DebetCard card = (DebetCard) product;
                System.out.println(card.getProductName());
                System.out.println("Годовое обслуживание: " + card.getAnnualCharge());
                System.out.println("Кэшбэк: " + card.getCashBackPercent() + "%\n");
            } else if (product instanceof Account) {
                Account account = (Account) product;
                System.out.println(account.getProductName());
                System.out.println("Годовое обслуживание: " + account.getAnnualCharge());
                System.out.println("Годовой процент: " + account.getPercent() + "%\n");
            }
        }
    }
}
