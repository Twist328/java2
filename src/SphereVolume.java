

public class SphereVolume {// объем шара

    //double radius;
    public SphereVolume() {
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
        System.out.println("Объем шара = " + volume);
//  без сканера
        double radius1 = 82;
        double volume1 = (4 * 3.14 * Math.pow(radius1, 3)) / 3;*/
        System.out.println("\n*********************************");
        System.out.println("Объем шара = " + new SphereVolume().volume(40));
        System.out.println("*********************************");
        System.out.println("Объем шара = " + new SphereVolume().volume1(79));
        System.out.println("*********************************");
    }
}

