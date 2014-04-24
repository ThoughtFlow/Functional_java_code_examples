package chapter7;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * This example shows how to implement a prime number finder using Java streams. Notice that sometimes, streams need to
 * be broken into two pieces. The first stream cycles through numbers 1 to 1000 while the second (isPrimeStream) cycles
 * through the numbers of the current number to check by checking for its divisors.
 */
public class PrimeFinder {
    private static boolean isPrimeImperative(int n) {
        // Treat n == 1 or 2 as special cases. Otherwise, use null value to mark answer as not yet known.
        Boolean isPrime = n <= 1 ? Boolean.FALSE : n == 2 ? Boolean.TRUE : null;

        int limit = (int) Math.sqrt(n);
        for (int i = 2; isPrime == null && i <= limit; ++i) {
            isPrime = n % i == 0 ? Boolean.FALSE : null;
        }

        return isPrime != Boolean.FALSE;
    }

    private static boolean isPrimeStream(int n) {
        // Treat n == 1 or 2 as special cases. Otherwise, use null value to mark answer as not yet known.
        Boolean isPrime = n <= 1 ? Boolean.FALSE : n == 2 ? Boolean.TRUE : null;

        return isPrime != null ? isPrime : IntStream.rangeClosed(2, (int) Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }

    public static void main(String[] args) {
        System.out.println("Here are the prime numbers below 200...");
        IntConsumer printLine = i -> System.out.print(i + ", ");
        IntStream.range(1, 200).filter(PrimeFinder::isPrimeImperative).forEach(printLine);
        System.out.println();
        IntStream.range(1, 200).filter(PrimeFinder::isPrimeStream).forEach(printLine);
        System.out.println();
    }
}