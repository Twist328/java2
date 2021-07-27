package ru.progwards.java2.lessons.testsjava1;

public class SphereVolume {// объем шара
    public SphereVolume() {
    }
    public double SvolumeErth(double diameter) { //площадь планеты
        return ( Math.PI * Math.pow(diameter, 2));
    }
    public double VvolumeErth(double diameter) { // объем  планеты
        return (4*Math.PI * Math.pow(diameter/2, 3))/3;
    }
    public double volume(double radius) {
        return (4 * Math.PI * Math.pow(radius, 3)) / 3;
    }

    public double volume1(double diameter) {
        return (4 * Math.PI * Math.pow(diameter / 2, 3)) / 3;
    }

    public static void main(String[] args) {
       /* Scanner sc = new Scanner(System.in);
        System.out.println("\n" + "Введите радиус шара:");
        double radius = sc.nextDouble();
        double volume = (4 * 3.14 * Math.pow(radius, 3)) / 3;// формула V= (4* PI (3,14)) * (rad * rad * rad)) /3;
        System.out.println("Объем шара = " + volume);*/

        System.out.println("\n*********************************");
        System.out.println("Объем шара = " + new SphereVolume().volume(40));
        System.out.println("*********************************");
        System.out.println("Объем шара = " + new SphereVolume().volume1(79));
        System.out.println("*********************************");
        System.out.println("Площадь земли = " + new SphereVolume().SvolumeErth(12742));
        System.out.println("*********************************");
        System.out.println("Объем земли = " + new SphereVolume().VvolumeErth(12742));
        System.out.println("*********************************");
    }
}

