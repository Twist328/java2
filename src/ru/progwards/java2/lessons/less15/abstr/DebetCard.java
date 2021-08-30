package ru.progwards.java2.lessons.less15.abstr;

import java.math.BigDecimal;

public interface DebetCard extends Product {
    BigDecimal getCashBackPercent(); // кэшбэк
}

