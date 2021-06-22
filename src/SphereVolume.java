import java.util.Scanner;

public class SphereVolume {  // объем шара
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n" + "Введите радиус шара:");
        double radius = sc.nextDouble();
        double volume = (4 * 3.14 * Math.pow(radius, 3)) / 3;// формула V= (4* PI (3,14)) * (rad * rad * rad)) /3;
        System.out.println("Объем шара = " + volume);
//  без сканера
        double radius1 = 88;
        double volume1 = (4 * 3.14 * Math.pow(radius1, 3)) / 3;
        System.out.println("*****************************************************");
        System.out.println("Объем шара = " + volume1);

    }

}

