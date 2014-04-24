package chapter5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

public class Composition {
    private static void consumer() {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        Consumer<Integer> printElement = System.out::print;
        Consumer<Integer> printParity = i -> System.out.print(" is " + (i % 2 == 0 ? "even" : "odd"));
        Consumer<Integer> printLineFeed = i -> System.out.println();
        Consumer<Integer> printFormattedParity = printElement.andThen(printParity).andThen(printLineFeed);
        Arrays.asList(1, 2, 3, 4, 5).forEach(printFormattedParity);
        printFormattedParity.accept(1);
    }

    private static void predicate() {
        IntPredicate isGreaterThanOne = i -> i > 1;
        IntPredicate isIndivisible = i ->
        {
            for (int n = 2; n <= Math.sqrt(i); ++n)
                if (i % n == 0)
                    return false;

            return true;
        };
        IntPredicate isPrime = isGreaterThanOne.and(isIndivisible);
        IntPredicate isComposite = isIndivisible.negate().and(isGreaterThanOne);
        System.out.println(isPrime.test(2));
        for (int i = 1; i < 100; i++)
            if (isPrime.test(i) == isComposite.test(i))
                System.out.println(i + ": " + isPrime.test(i) + " : " + isComposite.test(i));

    }

    private static void function() {
        IntUnaryOperator squareIt = i -> i * i;
        IntUnaryOperator doubleIt = i -> i * 2;

        IntUnaryOperator squared = doubleIt.compose(doubleIt);
        System.out.println(squared.applyAsInt(2));

        for (int i = 0; i < 5; ++i) {
            squared = squared.compose(squared);
            System.out.println(squared.applyAsInt(2));
        }

//        System.out.println(squared.applyAsInt(2));
    }

    private static void psGrep() {
        Function<List<String>, List<String>> ps = s -> new ArrayList<>(Arrays.asList("1001 java mySpringApplication", "1002 myOtherApp", "1003 java myApplicationServer"));
        Function<List<String>, List<String>> grep = list ->
        {
            list.removeIf(s -> !s.contains("java"));
            return list;
        };

        Function<List<String>, List<String>> listJavaProcs = ps.andThen(grep);

        for (String nextProcess : listJavaProcs.apply(null)) {
            System.out.println(nextProcess);
        }
    }

    private static void psGrep2() {
        Function<String, List<String>> ps = s -> new ArrayList<>(Arrays.asList("1001 java mySpringApplication", "1002 myOtherApp", "1003 java myApplicationServer"));
        Function<List<String>, List<String>> grep = list ->
        {
            list.removeIf(s -> !s.contains("java"));
            return list;
        };

        Function<String, List<String>> listJavaProcs = grep.compose(ps);
        listJavaProcs.apply(null).forEach(System.out::println);
    }


    private static void viWhich() {
        Function<String, String> which = s -> "/bin/" + s;
        Function<String, String> vi = s -> "File edited was : " + s;

        Function<String, String> viComposeWhich = vi.compose(which);
        Function<String, String> whichComposeVi = which.compose(vi);
        Function<String, String> viAndThenWhich = vi.andThen(which);
        Function<String, String> whichAndThenVi = which.andThen(vi);
        System.out.println(viComposeWhich.apply("myScript"));
        System.out.println(whichComposeVi.apply("myScript"));
        System.out.println(viAndThenWhich.apply("myScript"));
        System.out.println(whichAndThenVi.apply("myScript"));
    }

    public static void main(String... args) {
        consumer();
        predicate();
        function();
        psGrep();
        viWhich();
    }


}
