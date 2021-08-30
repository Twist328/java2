package ru.progwards.java2.lessons.less15.abstr;

import ru.progwards.java2.lessons.less15.abstr.Account;

import java.math.BigDecimal;

public class Bank1Account implements Account {
    @Override
    public String getProductName() {
        return "Накопительный счёт, Банк 1";
    }
    @Override
    public BigDecimal getAnnualCharge() {
        return BigDecimal. valueOf(500);
    }
    @Override
    public BigDecimal getPercent() {
        return BigDecimal. valueOf(5.5);
    }
}
