package ru.progwards.java2.lessons.less15.abstr;

import java.math.BigDecimal;

public interface Product {
    // имя банковского продукта
    String getProductName();
    // стоимость годового обслуживания
    BigDecimal getAnnualCharge();
}

