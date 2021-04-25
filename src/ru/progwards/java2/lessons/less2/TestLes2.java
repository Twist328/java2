package ru.progwards.java2.lessons.less2;

import java.util.ArrayList;
import java.util.List;

public class TestLes2 {
    class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            return name + " " + age;
        }
    }
    //Создайте метод, используя лямбда, с сигнатурой void sortAndPrint(List<Person> list), который
    // вначале сортирует list по возрасту, а затем выводит его на консоль.

    void sortAndPrint(List<Person> list) {
        list.sort((a, b)->Integer.compare(a.age, b.age));
       // list.sort(Comparator.comparingInt(a -> a.age));
        //list.forEach(a->System.out.println(a));
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
        new TestLes2().test();
    }
    void test() {
        List<Person> list = new ArrayList<>(List.of(
                new Person("Петров А.В", 27),
                new Person("Иванова А.Я.", 33),
                new Person("Боширов Р.В.", 18),
                new Person("Трамп Д.П.", 88),
                new Person("Монро М.Р.", 35)
        ));
        sortAndPrint(list);
    }
}

