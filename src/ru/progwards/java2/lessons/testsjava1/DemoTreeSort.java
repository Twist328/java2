package ru.progwards.java2.lessons.testsjava1;

import java.util.Arrays;
import java.util.Random;
public class DemoTreeSort {


        private static final int CONSOLE_WIDTH = 80;
        private static final int DISTINCT_INTS = 500;
        private static final int LENGTH = 10_000_000;

        public static void main(String[] args) throws InterruptedException {
            System.out.println(title("Small number of distinct integers"));
            int[] array = new int[LENGTH];
            long seed = System.nanoTime();
            Random random = new Random(seed);

            for (int i = 0; i < array.length; ++i) {
                array[i] = 3 * random.nextInt(DISTINCT_INTS);
            }

            System.out.println("Seed = " + seed);

            profile(array);
        }

        private static void profile(int[] array) throws InterruptedException {
            int[] array2 = array.clone();

            long startTime = System.nanoTime();
            Arrays.sort(array);
            long endTime = System.nanoTime();

            System.out.printf("Arrays.sort in %.2f milliseconds.\n",
                    (endTime - startTime) / 1E6);

            startTime = System.nanoTime();
            IntTreeSort.sort(array2);
            endTime = System.nanoTime();

            System.out.printf("IntTreeSort.sort in %.2f milliseconds.\n",
                    (endTime - startTime) / 1E6);

            System.out.println("Equals: " + Arrays.equals(array, array2));
        }

        public static String title(String text) {
            return title(text, '=');
        }

        private static String title(String text, char c) {
            StringBuilder sb = new StringBuilder();

            int left = (CONSOLE_WIDTH - 2 - text.length()) / 2;
            int right = CONSOLE_WIDTH - 2 - text.length() - left;

            for (int i = 0; i < left; ++i) {
                sb.append(c);
            }

            sb.append(' ').append(text).append(' ');

            for (int i = 0; i < right; ++i) {
                sb.append(c);
            }

            return sb.toString();
        }
    }
