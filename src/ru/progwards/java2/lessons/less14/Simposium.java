package ru.progwards.java2.lessons.less14;

import java.util.SplittableRandom;

public class Simposium {

    // вилка
    static class Fork {
        boolean areFree = true;

        private boolean areFree() {
            return areFree;
        }

        private void setFree(boolean free) {
            areFree = free;
        }
    }

    // философ
    static class Philosopher {

        String name;

        Fork right;// вилка справа
        Fork left;// вилка слева

        long reflectTime;// отрезок времени, когда философ размышляет в мс
        long eatTime;// отрезок времени, когда философ ест в мс

        long reflectSum;// суммарное время, которое философ размышлял в мс
        long eatSum;// суммарное время, которое философ ел в мс

        final static long INTERVALTIME = 500;

        //размышлять. Выводит "размышляет "+ name на консоль с периодичностью 0.5 сек
        void reflect() throws InterruptedException {
            long timeStart = System.currentTimeMillis();
            long timeNow = timeStart;
            long passed = 0;
            boolean isInterrupted = false;
            while (passed < reflectTime) {
                System.out.println("размышляет " + name);
                long needReflect = reflectTime - passed;
                try {
                    Thread.sleep(needReflect > INTERVALTIME ? INTERVALTIME : needReflect);
                } catch (InterruptedException e) {
                    isInterrupted = true;
                    break;
                } finally {
                    timeNow = System.currentTimeMillis();
                    passed = timeNow - timeStart;
                }
            }
            reflectSum += passed;
            if (isInterrupted) throw new InterruptedException();
        }

        //есть. Выводит "ест "+ name на консоль с периодичностью 0.5 сек
        void eat() throws InterruptedException {
            long timeStart = System.currentTimeMillis();
            long timeNow = timeStart;
            long passed = 0;
            boolean isInterrupted = false;
            while (passed < eatTime) {

                System.out.println("ест " + name);
                long needReflect = eatTime - passed;
                try {
                    /* Thread.sleep(needReflect > INTERVALTIME ? INTERVALTIME : needReflect);*/
                    if (needReflect > INTERVALTIME) {
                        Thread.sleep(INTERVALTIME);
                    } else
                        Thread.sleep(needReflect);
                } catch (InterruptedException e) {
                    isInterrupted = true;
                    break;
                } finally {
                    timeNow = System.currentTimeMillis();
                    passed = timeNow - timeStart;
                }
            }
            eatSum += passed;
            if (isInterrupted) throw new InterruptedException();
        }

        Philosopher(String name, Fork left, Fork right, long reflectTime, long eatTime) {
            this.reflectTime = reflectTime;
            this.eatTime = eatTime;
            reflectSum = 0;
            eatSum = 0;
            this.left = left;
            this.right = right;
            this.name = name;
        }

        Fork getFork(Direction side) {
            /*if (side.equals(Direction.LEFT)) {
                return left;
            } else return right;*/
            return side.equals(Direction.LEFT) ? left : right;
        }
    }

    enum Direction {LEFT, RIGHT}

    ;
    final static int PHILSCOUNT = 5; // количество философов
    Thread[] threads = new Thread[PHILSCOUNT]; // поток на каждого философа
    Philosopher[] philosophers = new Philosopher[PHILSCOUNT]; // философы
    SplittableRandom random = new SplittableRandom(); //ГСЧ

    //который инициализирует необходимое количество философов и вилок. Каждый философ выполняется в отдельном потоке.
    // reflectTime задает время в мс, через которое философ проголодается, eatTime задает время в мс,
    // через которое получив 2 вилки философ наестся и положит вилки на место
    Simposium(long reflectTime, long eatTime) {
        Fork[] forks = new Fork[PHILSCOUNT];
        for (int i = 0; i < PHILSCOUNT; i++) {
            forks[i] = new Fork();
        }
        for (int i = 0; i < PHILSCOUNT; i++) {
            final Philosopher phil = new Philosopher("PH" + (i + 1), forks[i], forks[(i + 1) % PHILSCOUNT], reflectTime, eatTime);
            philosophers[i] = phil;
            threads[i] = new Thread(new Algorithms(phil, random));
        }
    }

    // основная логика действий философа
    static class Algorithms implements Runnable {

        Philosopher phil;
        SplittableRandom random;
        Direction side = Direction.LEFT;//side - СТОРОНА (право-лево от философа, касается вилки)

        public Algorithms(Philosopher phil, SplittableRandom random) {
            this.phil = phil;
            this.random = random;
        }

        public Algorithms(Philosopher phil) {
            this.phil = phil;
            this.random = random;
        }

        @Override
        public void run() {
            while (true) {
                if (side.equals(Direction.LEFT)){
                   side.equals( Direction.RIGHT);
                }else
                    side.equals(Direction.LEFT);
               /* side = (side == Direction.LEFT ? Direction.RIGHT : Direction.LEFT);*/
                try {
                    Thread.sleep(random.nextInt(PHILSCOUNT));
                } catch (InterruptedException e) {
                    break;
                }
                // Ситуация: Нет ни одной вилки
                Fork dirRt = phil.getFork(side);
                synchronized (dirRt) {
                    // 1. ПОСМОТРИ НАПРАВО, если нет вилки, то через несколько случайное количество секунд повтори действие поменяв сторону (ПОСМОТРИ НАЛЕВО).
                    if (dirRt.areFree())
                        dirRt.setFree(false); // 2. Увидел вилку на столе то ПОПРОБУЙ ее взять. Если попытка неудачна то прекрати попытку и поменяв сторону вернись в п.1
                    else continue;
                }
                // Ситуация: В одной руке есть вилка.
                side = (side == Direction.LEFT ? Direction.RIGHT : Direction.LEFT);
                Fork dirLt = phil.getFork(side);
                synchronized (dirLt) {
                    // 3. УДАЧНО - есть одна вилка в руке! ПОСМОТРИ в ДРУГУЮ СТОРОНУ. Если нет вилки , тогда положи свою вилку назад.! И поменяв сторону вернись в п.1
                    if (dirLt.areFree())
                        dirLt.setFree(false); // 5. УДАЧНО - есть вторая вилка в руке!
                    else {
                        dirRt.setFree(true); // 4. ПОПРОБУЙ ВЗЯТЬ ВИЛКУ если неудачно, то ОТПУСТИ (освободи) эту вилку и ПОЛОЖИ ВИЛКУ КОТОРАЯ НАХОДИТСЯ В ДРУГОЙ РУКЕ. Поменяй сторону вернись в п.1
                        continue;
                    }
                }
                // Ситуация: В обеих руках по вилке.
                // 6. Приступай к еде
                try {
                    phil.eat();
                } catch (InterruptedException e) {
                    break;
                }
                phil.right.setFree(true);
                phil.left.setFree(true);
                // 7. Наелся положи обе вилки, сначала правую, затем левую на стол. Думай до тех пока не проголодаешься.
                try {
                    phil.reflect();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    // запускает философскую беседу
    void start() {
        for (int i = 0; i < PHILSCOUNT; i++) {
            threads[i].start();
        }
    }

    // завершает философскую беседу
    void stop() throws InterruptedException {
        for (int i = 0; i < PHILSCOUNT; i++) {
            threads[i].interrupt();
        }
        for (int i = 0; i < PHILSCOUNT; i++) {
            threads[i].join();
        }
    }

    //печатает результаты беседы в формате
    //Философ name, ел ххх, размышлял xxx
    //где ххх время в мс
    void print() {
        System.out.println("***********************************");
        for (Philosopher PH : philosophers) {
            System.out.println("Философ " + PH.name + ", ел " + PH.eatSum + ", размышлял " + PH.reflectSum);
            System.out.println("***********************************");
        }
    }

    // реализует тест для философской беседы. Проверить варианты, когда ресурсов (вилок) достаточно
    // (философы долго размышляют и мало едят) и вариант когда не хватает (философы много едят и мало размышляют)
    public static void main(String[] args) throws InterruptedException {
        Simposium simposium = new Simposium(1000, 1000);
        simposium.start();
        Thread.sleep(5000);
        simposium.stop();
        simposium.print();
    }


}
