import java.math.BigInteger;
import java.util.stream.Stream;

public class TestFibonacci {
    public static void main(String[] args) {
        //TestFibonacci testFibonacci = new TestFibonacci();
        BigInteger prev = BigInteger.ZERO;
        BigInteger count = BigInteger.ONE;
        BigInteger next = BigInteger.ZERO;
        int i;
        for (i = 0; i < 10; i++) {
            prev = count;
            count = next;
            next = prev.add(count);

            System.out.println("Число фибо:" + next);


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

        System.out.println("_______________\n");
        Stream.iterate(new long[]{1, 1}, arr -> new long[]{arr[1], arr[0] + arr[1]})
                .limit(10)
                .map(y -> y[0])
                .forEach(x -> System.out.println(x));
        System.out.println("_______________________________________\n");
        System.out.println(new TestFibonacci().fibonacci(50));
    }

    public static BigInteger[][] matrixMultiplication(BigInteger[][] a, BigInteger[][] b) {
        // [a00 * b00 + a01 * b10, a00 * b01 + a01 * b11]
        // [a10 * b00 + a11 * b10, a10 * b01 + a11 * b11]
        return new BigInteger[][]{
                {a[0][0].multiply(b[0][0]).add(a[0][1].multiply(b[1][0])), a[0][0].multiply(b[0][1]).add(a[0][1].multiply(b[1][1]))},
                {a[1][0].multiply(b[0][0]).add(a[1][1].multiply(b[1][0])), a[1][0].multiply(b[0][1]).add(a[1][1].multiply(b[1][1]))},
        };
    }

    // возведение матрицы размера 2 на 2 в степень n
    public static BigInteger[][] matrixPowerFast(BigInteger[][] a, int n) {
        if (n == 0) {
            // любая матрица в нулевой степени равна единичной матрице
            return new BigInteger[][]{
                    {BigInteger.ONE, BigInteger.ZERO},
                    {BigInteger.ZERO, BigInteger.ONE}
            };
        } else if (n % 2 == 0) {
            // a ^ (2k) = (a ^ 2) ^ k
            return matrixPowerFast(matrixMultiplication(a, a), n / 2);
        } else {
            // a ^ (2k + 1) = (a) * (a ^ 2k)
            return matrixMultiplication(matrixPowerFast(a, n - 1), a);
        }
    }

    // функция, возвращающая n-ое число Фибоначчи
    public static BigInteger fibonacci(int n) {
        if (n == 0) {
            return BigInteger.ZERO;
        }

        BigInteger[][] a = {
                {BigInteger.ONE, BigInteger.ONE},
                {BigInteger.ONE, BigInteger.ZERO}
        };

        BigInteger[][] powerOfA = matrixPowerFast(a, n - 1);
        // nthFibonacci = powerOfA[0][0] * F_1 + powerOfA[0][0] * F_0 = powerOfA[0][0] * 1 + powerOfA[0][0] * 0
        BigInteger nthFibonacci = powerOfA[0][0];
        return nthFibonacci;

    }
}
