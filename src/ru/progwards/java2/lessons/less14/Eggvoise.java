package ru.progwards.java2.lessons.less14;

class EggVoice extends Thread {
    @Override
    public void run()
    {
        for(int i = 0; i < 5; i++)
        {
            try{
                sleep(1000);		//Приостанавливает поток на 1 секунду
            }catch(InterruptedException e){}
            System.out.println("яйцо!");
            System.out.println("*******************");
        }
        //Слово «яйцо» сказано 5 раз
    }
}

class ChickenVoice{	//Класс с методом main()
    static EggVoice mAnotherOpinion;	//Побочный поток

    public static void main(String[] args) {
        mAnotherOpinion = new EggVoice();	//Создание потока
        System.out.println("\n*******************");
        System.out.println("Спор начат...");
        System.out.println("*******************");
        mAnotherOpinion.start(); 			//Запуск потока

        for(int i = 0; i < 5; i++)
        {
            try{
                Thread.sleep(1000);		//Приостанавливает поток на 1 секунду
            }catch(InterruptedException e){}
            System.out.println("курица!");
        }

        //Слово «курица» сказано 5 раз

        if(mAnotherOpinion.isAlive())	//Если оппонент еще не сказал последнее слово
        {
            try{
                mAnotherOpinion.join();	//остановить поток, подождав пока оппонент закончит высказываться.
            }catch(InterruptedException e){}

            System.out.println("Первым появилось яйцо!");

        }
        else	//если оппонент уже закончил высказываться
        {
            System.out.println("Первой появилась курица!");
        }
        System.out.println("*******************");
        System.out.println("Спор закончен!");
        System.out.println("*******************");
    }
}
