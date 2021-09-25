package ru.progwards.java2.lessons.less14;

import java.util.SplittableRandom;

class Simposium {
    static class Fork {
        boolean areFree = true;

        boolean areFree() {
            return areFree;

        }

        void setFree(boolean free) {
            areFree = free;

        }
    }

    static class Philosopher {
        String name;
        Fork right;
        Fork left;
        long reflectTime;
        long eatTime;
        long reflectSum;
        long eatSum;

        final static long INTERVALTIME = 500;

        void reflect() throws InterruptedException {
            long timeStart = System.currentTimeMillis();
            long timeNow = timeStart;
            long passed = 0;
            boolean isInterrupted = false;

            while (passed < reflectTime) {
                System.out.println("размышлял " + name);
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

        void eat() throws InterruptedException {
            long timeStart = System.currentTimeMillis();
            long timeNow = timeStart;
            long passed = 0;
            boolean isInterrupted = false;

            while (passed < eatTime) {
                System.out.println("eл " + name);
                long needReflect = eatTime - passed;
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
            eatSum += passed;
            if (isInterrupted) throw new InterruptedException();
        }

        Philosopher(String name, Fork right, Fork left, long eatTime, long reflectTime) {
            eatSum = 0;
            reflectSum = 0;
            this.left = left;
            this.right = right;
            this.reflectTime = reflectTime;
            this.eatTime = eatTime;
            this.name = name;
        }

        Fork getFork(Direction side) {
            return side.equals(Direction.LEFT) ? left : right;
        }
    }

    enum Direction {LEFT, RIGHT}

    ;
    final static int PHILSCOUNT = 5;
    Thread[] threads = new Thread[PHILSCOUNT];
    Philosopher[] philosophers = new Philosopher[PHILSCOUNT];
    SplittableRandom random = new SplittableRandom();

    Simposium(long eatTime, long reflectTime) {
        Fork[] forks = new Fork[PHILSCOUNT];
        for (int i = 0; i < PHILSCOUNT; i++) {
            forks[i] = new Fork();
        }
        for (int i = 0; i < PHILSCOUNT; i++) {
            final Philosopher phil = new Philosopher("PH" + (i + 1), forks[i], forks[(i + 1) % PHILSCOUNT], eatTime, reflectTime);
            philosophers[i] = phil;
            threads[i] = new Thread(new Algorithms(phil, random));
        }
    }

    static class Algorithms implements Runnable {
        Philosopher phil;
        Direction side = Direction.LEFT;
        SplittableRandom random;

        public Algorithms(Philosopher phil, SplittableRandom random) {
            this.phil = phil;
            this.random = random;
        }

        @Override
        public void run() {
            while (true) {
                side = side.equals(Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
                try {
                    Thread.sleep(random.nextInt(PHILSCOUNT));
                } catch (InterruptedException e) {
                    break;
                }
                Fork dirRight = phil.getFork(side);
                synchronized (dirRight) {
                    if (dirRight.areFree())
                        dirRight.setFree(false);
                    else continue;
                }
                side = side.equals(Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
                Fork dirLeft = phil.getFork(side);
                synchronized (dirLeft) {
                    if (dirLeft.areFree())
                        dirLeft.setFree(false);
                    else {
                        dirRight.setFree(true);
                        continue;
                    }
                }
                try {
                    phil.eat();
                } catch (InterruptedException e) {
                    break;
                }
                phil.right.setFree(true);
                phil.left.setFree(true);

                try {
                    phil.reflect();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    void start() {
        for (int i = 0; i < PHILSCOUNT; i++) {
            threads[i].start();
        }
    }

    void stop() throws InterruptedException {
        for (int i = 0; i < PHILSCOUNT; i++) {
            threads[i].interrupt();
        }
        for (int i = 0; i < PHILSCOUNT; i++) {
            threads[i].join();
        }
    }

    void print() {
        for (Philosopher PH : philosophers) {
            System.out.println("философ " + PH.name + ",ел " + PH.eatSum + ",размышлял " + PH.reflectSum);
        }
    }

    // вилка
   /* static class Fork {
        boolean areFree = true;

        boolean areFree() {
            return areFree;
        }

        void setFree(boolean free) {
            areFree = free;
        }
    }

    static class Philosopher {
        String name;
        Fork left;
        Fork right;
        long reflectTime;
        long eatTime;
        long reflectSum;
        long eatSum;
        final static long INTERVALTIME = 500;

        void reflect() throws InterruptedException {
            long timeStart = System.currentTimeMillis();
            long timeNow = timeStart;
            long passed = 0;
            boolean isInterrupted = false;

            while (passed < reflectTime) {
                System.out.println("размышлял " + name);
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

        void eat() throws InterruptedException {
            long timeStart = System.currentTimeMillis();
            long timeNow = timeStart;
            long passed = 0;
            boolean isInterrupted = false;

            while (passed < eatTime) {
                System.out.println("ел " + name);
                long needReflect = eatTime - passed;
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
            eatSum += passed;
            if (isInterrupted) throw new InterruptedException();
        }*/


      /*  Philosopher(String name, Fork right, Fork left, long eatTime, long reflectTime) {
            eatSum = 0;
            reflectSum = 0;
            this.reflectTime = reflectTime;
            this.eatTime = eatTime;
            this.name = name;
            this.right = right;
            this.left = left;
        }

        Fork getFork(Direction side) {
            return side.equals(Direction.LEFT) ? left : right;

        }
    }

    enum Direction {LEFT, RIGHT}

    ;
    final static int PHILSCOUNT = 5;
    Thread[] threads = new Thread[PHILSCOUNT];
    Philosopher[] philosophers = new Philosopher[PHILSCOUNT];
    SplittableRandom random = new SplittableRandom();

    Simposium(long reflectTime, long eatTime) {
        Fork[] forks = new Fork[PHILSCOUNT];
        for (int i = 0; i < PHILSCOUNT; i++) {
            forks[i] = new Fork();
        }
        for (int i = 0; i < PHILSCOUNT; i++) {
            final Philosopher phil = new Philosopher("PH" + (i + 1), forks[i], forks[(i + 1) % PHILSCOUNT], eatTime, reflectTime);
            philosophers[i] = phil;
            threads[i] = new Thread(new Algorithms(phil, random));
        }
    }*/

    // основная логика действий философа
    /*static class Algorithms implements Runnable {

        Philosopher phil;
        SplittableRandom random;
        Direction side = Direction.LEFT;//side - СТОРОНА (влево от философа, касается стороны от объекта (вилка)

        public Algorithms(Philosopher phil, SplittableRandom random) {
            this.phil = phil;
            this.random = random;
        }

        @Override
        public void run() {
            while (true) {

                side = side.equals(Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
                try {
                    Thread.sleep(random.nextInt(PHILSCOUNT));
                } catch (InterruptedException e) {
                    break;
                }
                // Ситуация: Нет ни одной вилки
                Fork dirRight = phil.getFork(side);//также dirRight - Сторона свободная от наличия вилки (правая)
                synchronized (dirRight) {
                    // 1. ПОСМОТРИ НАПРАВО, если нет вилки, то через несколько случайное количество секунд повтори действие поменяв сторону (ПОСМОТРИ НАЛЕВО).
                    if (dirRight.areFree())
                        dirRight.setFree(false); // 2. Увидел вилку на столе то ПОПРОБУЙ ее взять. Если попытка неудачна то прекрати попытку и поменяв сторону вернись в п.1
                    else continue;
                }
                // Ситуация: В одной руке есть вилка.
                side = side.equals(Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
                Fork dirLeft = phil.getFork(side);
                synchronized (dirLeft) {
                    // 3. УДАЧНО - есть одна вилка в руке! ПОСМОТРИ в ДРУГУЮ СТОРОНУ. Если нет вилки , тогда положи свою вилку назад.! И поменяв сторону вернись в п.1
                    if (dirLeft.areFree())
                        dirLeft.setFree(false); // 5. УДАЧНО - есть вторая вилка в руке!
                    else {
                        dirRight.setFree(true); // 4. ПОПРОБУЙ ВЗЯТЬ ВИЛКУ если неудачно, то ОТПУСТИ (освободи) эту вилку и ПОЛОЖИ ВИЛКУ КОТОРАЯ НАХОДИТСЯ В ДРУГОЙ РУКЕ. Поменяй сторону вернись в п.1
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
    }*/

    //печатает результаты беседы в формате
    //Философ name, ел ххх, размышлял xxx
    //где ххх время в мс
   /* void print() {
        System.out.println("***********************************");
        for (Philosopher PH : philosophers) {
            System.out.println("Философ " + PH.name + ", ел " + PH.eatSum + ", размышлял " + PH.reflectSum);
            System.out.println("***********************************");
        }
    }*/

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