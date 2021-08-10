package ru.progwards.java2.lessons.less13.example;
/*Блокировать доступ к ресурсам можно на уровне метода и класса. Следующий код показывает, что если во время выполнения
 программы имеется несколько экземпляров класса DemoClass, то только один поток может выполнить метод demoMethod(), для
 других потоков доступ к методу будет заблокирован. Это необходимо когда
 требуется сделать определенные ресурсы потокобезопасными.*/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DemoClass
{
    public synchronized static void demoMethod(){
        // ...
    }
}
// или
 class DemoClass1
{
    public void demoMethod1(){
        synchronized (DemoClass1.class) {
            // ...
        }
    }
}
//***************************************************************
