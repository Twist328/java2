package ru.progwards.java2.lessons.less10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResearchSafeVarargs1 {
    public static class Safe<T> {
        private List<T> list = new ArrayList<>();

        @SafeVarargs
        public final List<T> toList(T... toAdd) {
            list.addAll(Arrays.asList(toAdd));
            return list;
        }
        public List<T> normalUse(T elem) {
            // Теперь всё правильно, тип будет известен
            List<T> list = toList(elem, elem, elem, elem, elem);
            return list;
        }
    }

    @Schedule()
    @Schedule(dayOfWeek="Fri", time="12:34:56")
    @Schedule(dayOfWeek="Fri", time="05:55:55")
    public void action() {
        // что-то делаем
    }

    public static void main(String[] args) {
        Safe<String> safe = new Safe<>();
        var list = safe.normalUse("строка");
        System.out.println(list);
    }
}
