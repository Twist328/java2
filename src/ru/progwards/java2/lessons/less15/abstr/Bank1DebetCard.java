package ru.progwards.java2.lessons.less15.abstr;

import java.math.BigDecimal;

public class Bank1DebetCard implements DebetCard {
    @Override
    public String getProductName() {
        return "Дебетовая карта Банк 1";
    }
    @Override
    public BigDecimal getAnnualCharge() {
        return BigDecimal. valueOf(750);
    }
    @Override
    public BigDecimal getCashBackPercent() {
        return BigDecimal. valueOf(0.5);
    }
}

