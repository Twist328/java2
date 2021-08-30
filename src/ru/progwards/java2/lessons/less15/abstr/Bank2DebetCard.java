package ru.progwards.java2.lessons.less15.abstr;

import java.math.BigDecimal;

public class Bank2DebetCard implements DebetCard {

    @Override
    public String getProductName() {
        return "Дебетовая карта Банк 2";
    }

    @Override
    public BigDecimal getAnnualCharge() {
        return BigDecimal. valueOf(350);
    }
    @Override
    public BigDecimal getCashBackPercent() {
        return BigDecimal. ZERO;
    }
}


