package chapter8;

import java.util.function.BiFunction;
import java.util.function.IntFunction;

public class CurryingTest {

    private void curry() {
        // Currying - because we apply 1 parameter at a time
        IntFunction<IntFunction<Integer>> subtraction = x -> y -> x - y;
        System.out.println(subtraction.apply(4).apply(5));

        // Same thing without curring but using a BiFunction instead
        BiFunction<Integer, Integer, Integer> subtraction2 = (x, y) -> x - y;
        System.out.println(subtraction2.apply(4, 5));

        // Partial application - because we apply 2 parameters: apply(2, 4)
        BiFunction<Integer, Integer, IntFunction<Integer>> volume = (x, y) -> z -> x * y * z;
        System.out.println(volume.apply(2, 4).apply(6));

        // If you don't like the generics, roll your own functional interfaces
        MyBiFunction volume2 = (x, y) -> z -> x * y * z;
        System.out.println(volume2.apply(2, 4).apply(6));
    }

    public static void main(String... args) {
        new CurryingTest().curry();
    }
}
