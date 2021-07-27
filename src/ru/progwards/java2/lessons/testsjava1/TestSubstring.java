package ru.progwards.java2.lessons.testsjava1;

public class TestSubstring {
    public class MiddleStrTest {
    }
    public static void main(String args[]){
        String testString = "КАКЖЕТЫЗАДОЛБАЛ";
        System.out.println(testString.substring(0,5));
        System.out.println(testString.substring(1,5));
        System.out.println(testString.substring(2,5));
        System.out.println(testString.substring(0,6));
        System.out.println(testString.substring(1,6));
        System.out.println(testString.substring(2,6));
        System.out.println(testString.substring(0,7));
        System.out.println(testString.substring(1,7));
        System.out.println(testString.substring(2,15));
        System.out.println("______________________________________");
        String Str = new String("Добро пожаловать на Progwards.ru");

        System.out.print("Возвращаемое значение: ");
        System.out.println(Str.substring(10));

        System.out.print("Возвращаемое значение: ");
        System.out.println(Str.substring(8, 10));
        System.out.println("______________________________________");
      System.out.println("A       --> " + getMiddleString("A"));
      System.out.println("AB      --> " + getMiddleString("AB"));
      System.out.println("ABC     --> " + getMiddleString("ABC"));
      System.out.println("ABCD    --> " + getMiddleString("ABCD"));
      System.out.println("ABCDE   --> " + getMiddleString("ABCDE"));
      System.out.println("ABCDEF  --> " + getMiddleString("ABCDEF"));
      System.out.println("ABCDEFG --> " + getMiddleString("ABCDEFG"));
    }
    private static String getMiddleString(String str) {
        if (str.length() <= 2) {
            return str;
        }
        int beginIndex = (str.length() - 1) / 2;
        int endIndex = beginIndex + 2 - (str.length() % 2);
        return str.substring(beginIndex, endIndex);



    }
}

