package ru.progwards.java2.lessons.less2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperatorColan { //colan это :: обозначение оператора
    static boolean isEven(Integer i1) {
        return i1 % 2 == 0;
    }
    public static void main(String[] args) {
        Predicate<Integer> predicate = OperatorColan::isEven;
        // аналогично лямбда-выражению:
        // Predicate<Integer> predicate = x -> isEven(x);
        System.out.println(predicate.test(1000));
        Consumer<Book> p = System.out::println;
        p.accept(new Book("Капитанская дочка", "Пушкин", 545));
        System.out.println("______________________________________________________________");
        List<Book> list = new ArrayList<>(List.of(
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        list.sort(Comparator.comparing(a -> a.name));
        //list.forEach(element -> System.out.println(element));
        list.forEach(System.out::println);
        System.out.println("____________________________________________________________");
        List<Book> list1 = new ArrayList<>(List.of(                  //Пример фильтра по списку с циклом...
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        // формирование списка по критерию
        List<Book> filtered = new ArrayList<>();
        for (Book elem : list) {
            if (elem.name.contains("Ка")) {
                filtered.add(elem);
            }
        }
        // вывод на консоль
        filtered.forEach(System.out::println);
        System.out.println("________________________________________________________________________");
        List<Book> list2 = new ArrayList<>(List.of(                        //… и с потоком + ƛ-выражением
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        // формирование списка по критерию через поток
        List<Book> filtered1 =
                list2.stream().filter(x -> x.name.contains("Ка")).collect(Collectors.toList());
        // вывод на консоль
        filtered1.forEach(System.out::println);
        System.out.println("___________________________________________________________________");

            List<Book> list3 = new ArrayList<>(List.of(    //Промежуточные (отложенные) операции
                    new Book("Капитанская дочка", "Пушкин", 545),
                    new Book("Игрок", "Достоевский", 571),
                    new Book("Кавказский пленник", "Лермонтов", 597),
                    new Book("Мёртвые души", "Гоголь", 842),
                    new Book("Облако в штанах", "Маяковский", 495)
            ));
            // количество подходящих элементов через поток
            long num = list3.stream().filter(x -> x.name.contains("а")).count(); //list3.stream().filter(x -> x.name.contains("а") отложенная
        //.count(); терминальная
            System.out.println("Отобрано книг: " + num);
        System.out.println("_____________________________________________________________________");

            List<Book> list4 = new ArrayList<>(List.of(   //Исследуем отложенные операции
                    new Book("Капитанская дочка", "Пушкин", 545),
                    new Book("Игрок", "Достоевский", 571),
                    new Book("Кавказский пленник", "Лермонтов", 597),
                    new Book("Мёртвые души", "Гоголь", 842),
                    new Book("Облако в штанах", "Маяковский", 495)
            ));
            // лямбда
            Predicate<Book> preLambda = x -> {
                System.out.println(x);
                return x.name.contains("а");
            };
            // количество подходящих элементов через поток
            System.out.println("До stream().filter");
            Stream<Book> test = list.stream().filter(preLambda);
            System.out.println("После stream().filter");
            long num1 = test.count();
            System.out.println("Отобрано книг: " + num1);
        System.out.println("___________________________________________________________");
        List<Book> list5= new ArrayList<>(List.of(                      //Применим несколько фильтров
                new Book("Капитанская дочка", "Пушкин", 545),
                new Book("Игрок", "Достоевский", 571),
                new Book("Кавказский пленник", "Лермонтов", 597),
                new Book("Мёртвые души", "Гоголь", 842),
                new Book("Облако в штанах", "Маяковский", 495)
        ));
        Predicate<Book> preLamda1= x -> {
            System.out.println(x);
            return x.name.contains("а");
        };
// количество подходящих элементов через поток
        System.out.println("До stream().filter");
        Stream<Book> test1 = list.stream().filter(preLamda1).filter(x -> x.price < 555);
        System.out.println("После stream().filter");
        long num2 = test1.count();
        System.out.println("Отобрано книг: " + num2);
        }

    }





