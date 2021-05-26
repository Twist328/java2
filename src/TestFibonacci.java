import java.math.BigInteger;

public class TestFibonacci {
    public static void main(String[] args) {
        //TestFibonacci testFibonacci = new TestFibonacci();
        BigInteger prev = BigInteger.ZERO;
        BigInteger count = BigInteger.ONE;
        BigInteger next = BigInteger.ZERO;
        int i;
        for (i = 0; i < 100; i++) {
            prev = count;
            count = next;
            next = prev.add(count);

            System.out.println("Число фибо:" + next );

        }
        System.out.println("_______________\n");
           // TestFibonacci testFibonacci1 = new TestFibonacci();
            BigInteger prev1 = BigInteger.ONE;
            BigInteger count1 = BigInteger.ONE;
            BigInteger next1;
            for (int j = 2; j < 10; j++) {
                next1 = prev1.add(count1);
                prev1 = count1;
                count1 = next1;
            }
            System.out.println("Число fibo =" + count1);

        }
    }


