package ru.progwards.java2.lessons.less10;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    @First(name = "First Annotation", value = "Новое значение")
    public void getEmployee() {
        System.out.println("employee");
    }
}
