package ru.progwards.java2.lessons.less13;

class ThreadExample extends Thread {    /*Разработчики Java предоставили две возможности создания потоков : расширение
(extends) класса Thread и реализацию (implements) интерфейса Runnable. Расширение класса Thread - это путь наследования
методов и переменных класса родителя. В этом случае можно наследоваться только от одного родительского класса Thread.
 Данное ограничение Java можно преодолеть реализацией интерфейса Runnable, который содержит только один метод run().
Пример создания потока с использованием класса Thread :*/
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                // Приостанавливаем поток
                sleep(1000);
                System.out.println("thread ...");
            } catch (InterruptedException e) {
            }
        }
    }


    public static void main(String[] args) {
        // Создание потока
        ThreadExample example = new ThreadExample();
        // Запуск потока
        example.start();
    }
}
//******************************************************************
class RunnableExample implements Runnable {         /*Зачем нужно два вида реализации многопоточности; какую из них и когда
использовать? Реализация интерфейса Runnable используется в случаях, когда класс уже наследует какой-либо родительский
 класс и не позволяет расширить класс Thread. К тому же, хорошим тоном программирования в java считается реализация
 интерфейсов. Это связано с тем, что в java может наследоваться только один родительский класс. Таким образом,
 унаследовав класс Thread, невозможно наследовать какой-либо другой класс. Расширение класса Thread целесообразно
 использовать в случае, когда необходимо переопределить другие методы класса, помимо метода run().*/
    //Пример создания потока с использованием интерфейса Runnable :
    Thread thread;

    RunnableExample() {
        thread = new Thread(this, "Runnable поток");
        thread.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                // Приостанавливаем поток
                Thread.sleep(1000);
                System.out.println("Runnable поток");
            } catch (InterruptedException e) {
            }
        }
    }


    public static void main(String[] args) {
        // Создание потока
        new RunnableExample();
    }
}