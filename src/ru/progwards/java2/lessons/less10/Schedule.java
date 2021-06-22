package ru.progwards.java2.lessons.less10;

import java.lang.annotation.Repeatable;

@Repeatable(Schedules.class)
public @interface Schedule {
    String dayOfWeek() default "Понедельник";
    String time() default "00:00:00";
}
