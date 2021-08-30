package ru.progwards.java2.lessons.less15.abstr;

import java.math.BigDecimal;

public interface Account extends Product {
    BigDecimal getPercent(); // годовой процент
}
