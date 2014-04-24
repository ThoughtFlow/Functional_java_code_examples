package chapter5;

import java.util.function.IntPredicate;

public class PredicateComposition {

    private boolean isPrime(int number) {
        IntPredicate isGreaterThanOne = i -> i > 1;
        IntPredicate isIndivisible = i ->
        {
            for (int n = 2; n <= Math.sqrt(i); ++n)
                if (i % n == 0)
                    return false;

            return true;
        };
        IntPredicate isPrime = isGreaterThanOne.and(isIndivisible);
        return isPrime.test(number);
    }

    public static void main(String...args)
    {
        PredicateComposition predicateComposition = new PredicateComposition();

        for (int index = 0; index < 10; ++index) {
            System.out.println("Is " + index + " prime: " + predicateComposition.isPrime(index));
        }
    }
}
