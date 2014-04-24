package chapter7;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.ObjLongConsumer;
import java.util.stream.LongStream;

public class PerfectNumberFinder {

    private static boolean isPerfectImperative(long n)
    {
        long sum = 0;
        for (long i = 1; i <= n / 2; i++) // rangeClosed() operation
        {
            if (n % i == 0) // filter() operation
            {
                sum += i; // reduce() operation
            }
        }

        return sum == n;
    }

    private static boolean isPerfectStream(long n)
    {
        return LongStream.rangeClosed(1, n / 2).
            filter(i -> n % i == 0).
                reduce(0, (l, r) -> l + r) == n && n > 0;
    }

    private static boolean isPerfectParallel(long n) {
        return
            LongStream.rangeClosed(1, n / 2).
                parallel().
                    filter(i -> n % i == 0).
                        reduce(0, (l, r) -> l + r) == n && n > 0;
    }

    private static List<Long> findPerfectNumbers(long maxLong, LongPredicate condition) {
        return LongStream.rangeClosed(1, maxLong).
            filter(condition).
            collect(ArrayList<Long>::new,
                    (ObjLongConsumer<ArrayList<Long>>) ArrayList<Long>::add,
                    ArrayList<Long>::addAll);
    }



    private static <T> Holder<T> timeExecution(long n, String algoDescription, LongFunction<T> perfectAlgorithm) {
        long startTime = System.currentTimeMillis();
        T returnValue = perfectAlgorithm.apply(n);
        long duration = System.currentTimeMillis() - startTime;
        return new Holder<T>(returnValue, n, algoDescription, duration);
    }

    private static List<Long> findPerfectNumbersParallel(long n, LongFunction<Boolean> perfectAlgorithm) {
        LongPredicate predicate = perfectAlgorithm::apply;
        return
            LongStream.rangeClosed(1, n).
                parallel().
                    filter(predicate).
                        collect(ArrayList<Long>::new, (ObjLongConsumer<ArrayList<Long>>) ArrayList<Long>::add, ArrayList<Long>::addAll);
    }

    private static class Holder<T> {
        private final T t;
        private final long number;
        private final String algoDescription;
        private final long duration;

        private Holder(T t, long number, String algoDescription, long duration) {
            this.t = t;
            this.number = number;
            this.algoDescription = algoDescription;
            this.duration = duration;
        }

        public String getAlgoDescription() {
            return algoDescription;
        }

        public long getNumber() {
            return number;
        }

        public long getDuration() {
            return duration;
        }

        public T getT() {
            return t;
        }
    }

    private static void formatBoolean(Holder<Boolean> result) {
        System.out.println(result.getNumber() + " isPerfectSerialized: " + result.getT() + " Algorithm: " + result.algoDescription + " Duration: " + result.getDuration());
    }

    private static void formatList(Holder<List<Long>> results) {
        System.out.print(results.getNumber() + " PerfectList: ");
        results.getT().forEach(l -> System.out.print(l + ", "));
        System.out.print(", Algorithm: " + results.getAlgoDescription());
        System.out.println(" Duration: " + results.getDuration());
    }

    public static void main(String[] args) {

        // Time the duration of these 3 algorithms. Run them 5 times to get a better sample
        LongStream.rangeClosed(1, 4).flatMap(l -> LongStream.of(496, 8128, 33550336, 8589869056l)).forEach(l ->
        {
            formatBoolean(timeExecution(l, "isPerfectSerialized (imperative)", PerfectNumberFinder::isPerfectImperative));
            formatBoolean(timeExecution(l, "isPerfectSerialized (serialized)", PerfectNumberFinder::isPerfectStream));
            formatBoolean(timeExecution(l, "isPerfectSerialized (parallelized)", PerfectNumberFinder::isPerfectParallel));
        });

        // Time the duration of these 3 algorithms
        formatList(timeExecution(8128, "Serial find", l -> findPerfectNumbers(l, PerfectNumberFinder::isPerfectParallel)));
        formatList(timeExecution(8128, "Parallel find", l -> findPerfectNumbersParallel(l, PerfectNumberFinder::isPerfectParallel)));
        formatList(timeExecution(8128, "Serial find", l -> findPerfectNumbers(l, PerfectNumberFinder::isPerfectParallel)));
    }
}
