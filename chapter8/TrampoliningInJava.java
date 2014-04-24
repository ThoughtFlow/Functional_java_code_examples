package chapter8;

import java.math.BigInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Example showing how to implement trampolining in Java. In Java, recursion must be used with care because there is the
 * possibility of stack overflow. Trampolining is a technique for non-tail-call optimization languages, such as Java, to
 * get around this limitation.
 */
public class TrampoliningInJava {
    public static Supplier<?> factorial(BigInteger n, BigIntegerWrapper sum) {
        sum.holder = sum.holder.multiply(n);
        return n.intValue() > 1 ? () -> factorial(n.subtract(BigInteger.ONE), sum) : null;
    }

    private static class BigIntegerWrapper {
        BigInteger holder = BigInteger.valueOf(1);
    }

    private static long classicFactorial(long n, long accumulate) {
        return n == 1 ? 1 : classicFactorial(n - 1, n * accumulate);
    }

    private static BigInteger streamFactorial(long n) {
        return Stream.iterate(BigInteger.valueOf(n), next -> next.subtract(BigInteger.ONE)).limit(n).reduce(BigInteger::multiply).get();
    }

    public static void main(String[] args) {
        // Classic factorial - with stack overflow
//        System.out.println(classicFactorial(15000, 1));

        // Factorial with a trampoline
        Consumer<Supplier<?>> trampoline = supplier ->
        {
            while (supplier != null) {
                supplier = (Supplier) supplier.get();
            }
        };

        // Works with large numbers
        BigIntegerWrapper wrapper = new BigIntegerWrapper();
        trampoline.accept(factorial(BigInteger.valueOf(15000), wrapper));
        System.out.println(wrapper.holder);

        // Factorial done with streams
        System.out.println(streamFactorial(15000));
    }
}
