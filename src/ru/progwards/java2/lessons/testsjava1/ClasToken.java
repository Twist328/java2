package ru.progwards.java2.lessons.testsjava1;
import java.util.*;

public class ClasToken {

    public static void main(String[] args) {

        List<String> words = new ArrayList<String>();
        String str = "Breakpoint 10, main () at file.c:10";
        StringTokenizer st = new StringTokenizer(str);
        {
            while (st.hasMoreElements()) {
                words.add(st.nextToken());
            }

            String result = words.get(0);
            String result1 = words.get(1);
            String result2 = words.get(2);
            String result3 = words.get(3);
            String result4 = words.get(5);
            String result5 = words.get(4);

            System.out.println("\nЭлемент с  индексом 0 = "+ result);
            System.out.println("Элемент с  индексом 1 = "+ result1);
            System.out.println("Элемент с  индексом 2 = "+ result2);
            System.out.println("Элемент с  индексом 3 = "+ result3);
            System.out.println("Элемент с  индексом 5 = "+ result4);
            System.out.println("Элемент с  индексом 4 = "+ result5);
        }
    }
}