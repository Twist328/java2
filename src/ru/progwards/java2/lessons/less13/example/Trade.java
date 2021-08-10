package ru.progwards.java2.lessons.less13.example;

public class Trade
{
    public static void main(String[] args)
    {
        Store     store    = new Store();
        Producer  producer = new Producer(store);
        Consumer  consumer = new Consumer(store);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
/*// Daemon Java приложение завершает работу тогда, когда завершает работу последний его поток.
Даже если метод main() уже завершился, но еще выполняются порожденные им потоки, система будет ждать их завершения.

Однако это правило не относится к потоков-демонам (daemon). Если завершился последний обычный поток процесса, и остались
 только daemon потоки, то они будут принудительно завершены и выполнение приложения закончится. Чаще всего daemon
 потоки используются для выполнения фоновых задач, обслуживающих процесс в течение его жизни.
Объявить поток демоном достаточно просто. Для этого нужно перед запуском потока вызвать его метод
setDaemon(true). Проверить, является ли поток daemon'ом можно вызовом метода isDaemon().
В качестве примера использования daemon-потока можно рассмотреть класс Trade, который принял бы следующий вид :
/*public class Trade
{
    public static void main(String[] args)
    {
        Producer  producer = new Producer(store);
        Consumer  consumer = new Consumer(store);

//		new Thread(producer).start();
//		new Thread(consumer).start();

        Thread  tp = new Thread(producer);
        Thread  tc = new Thread(consumer);

        tp.setDaemon(true);
        tc.setDaemon(true);

        tp.start();
        tc.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}

        System.out.println("\nГлавный поток завершен\n");
        System.exit(0);
    }
}*/
