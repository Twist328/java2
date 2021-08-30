package ru.progwards.java2.lessons.less15.abstr;

import java.math.BigDecimal;

public class Bank3DebetCard implements DebetCard {
    @Override
    public String getProductName() {
        return "Дебетовая карта Банк 3";
    }
    @Override
    public BigDecimal getAnnualCharge() {
        return BigDecimal. valueOf(2500);
    }
    @Override
    public BigDecimal getCashBackPercent() {
        return BigDecimal. ONE;
    }
}
