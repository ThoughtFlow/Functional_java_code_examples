package chapter8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionalQuickSort {
    public static List<Integer> functionalSort(List<Integer> array) {
        if (array.size() > 1) {
            int mid = array.get(array.size() / 2);

            Map<Integer, List<Integer>> map = array.stream().
                collect(Collectors.groupingBy(i -> i < mid ? 0 : i == mid ? 1 : 2));

            // Conciseness is compromised for the following reasons:
            // - addAll does not return self. It is an OO artifact storing state
            List<Integer> left = functionalSort(map.getOrDefault(0, new ArrayList<>()));
            List<Integer> middle = map.getOrDefault(1, new ArrayList<>());
            List<Integer> right = functionalSort(map.getOrDefault(2, new ArrayList<>()));
            left.addAll(middle);
            left.addAll(right);

            return left;
        }
        else {
            return array;
        }
    }

    public static List<Integer> curriedSort(List<Integer> array) {
        if (array.size() > 1) {
            int mid = array.get(array.size() / 2);

            Map<Integer, List<Integer>> map = array.stream().
                collect(Collectors.groupingBy(i -> i < mid ? 0 : i == mid ? 1 : 2));
            Function<List<Integer>, Function<List<Integer>, Function<List<Integer>, List<Integer>>>> merge = left -> middle -> right -> {
                left.addAll(middle);
                left.addAll(right);
                return left;
            };

            return
                merge.apply(curriedSort(map.getOrDefault(0, new ArrayList<>()))).
                    apply(map.getOrDefault(1, new ArrayList<>())).
                    apply(curriedSort(map.getOrDefault(2, new ArrayList<>())));

        }
        else {
            return array;
        }
    }

    public static List<Integer> imperativeQuickSort(List<Integer> array, int low, int n) {
        int lo = low;
        int hi = n;

        if (lo >= n) {
            return array;
        }

        int mid = array.get((lo + hi) / 2);
        while (lo < hi) {
            while (lo < hi && array.get(lo) < mid) {
                lo++;
            }
            while (lo < hi && array.get(hi) > mid) {
                hi--;
            }
            if (lo < hi) {
                // Swap values
                int temp = array.get(lo);
                array.set(lo, array.get(hi));
                array.set(hi, temp);
                lo++;
                hi--;
            }
        }

        if (hi < lo) {
            lo = hi;
        }

        imperativeQuickSort(array, low, lo);
        imperativeQuickSort(array, lo == low ? lo + 1 : lo, n);

        return array;
    }

    public static List<Integer> getIntegers(int length) {
        ArrayList<Integer> integerList = new ArrayList<>(length);
        Random random = new Random(System.currentTimeMillis());

        IntStream.iterate(1, i -> ++i).limit(length).map((i) -> random.nextInt(length)).
            forEach(integerList::add);

        return integerList;
    }

    public static List<Integer> timedSort(List<Integer> integers, Function<List<Integer>, List<Integer>> mapper) {
        long startTime = System.currentTimeMillis();
        List<Integer> sortedList = mapper.apply(integers);
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Duration: (ms) " + duration);

        return sortedList;
    }

    public static void main(String[] args) {
        // Note that in a non-functional style, you would store the function to call in the object, then call timedsort.
        // In functional, we pass that as parameters.
        List<Integer> integers1 = getIntegers(1000000);
        ArrayList<Integer> integers2 = new ArrayList<>(integers1);
        ArrayList<Integer> integers3 = new ArrayList<>(integers1);
        ArrayList<Integer> warmUp = new ArrayList<>(integers1);

        timedSort(warmUp, FunctionalQuickSort::curriedSort);

        timedSort(integers1, (i) -> imperativeQuickSort(i, 0, i.size() - 1));//.forEach(printer);
        timedSort(integers2, FunctionalQuickSort::functionalSort);//.forEach(printer);
        timedSort(integers3, FunctionalQuickSort::curriedSort);//.forEach(printer);

        // Test that the ArrayList are sorted
        System.out.println("Is sorted: " + integers1.stream().reduce((l, r) -> l > -1 && l <= r ? r : -1).isPresent());
        System.out.println("Is sorted: " + integers2.stream().reduce((l, r) -> l > -1 && l <= r ? r : -1).isPresent());
        System.out.println("Is sorted: " + integers3.stream().reduce((l, r) -> l > -1 && l <= r ? r : -1).isPresent());
    }
}
