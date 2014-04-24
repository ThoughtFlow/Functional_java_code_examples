package chapter6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExamples {

    public static void testBuildOperations() {
        // Create a stream of integers 1, 2, and 3
        Stream.of(1, 2, 3);

        // Store the streamed Integers inside an array
        Integer[] integers = Stream.of(1, 2, 3).toArray(Integer[]::new);

        // An infinite stream
        Stream.iterate(1, i -> ++i);

        // Stream to arrays
        integers = Stream.of(1, 2, 3).toArray(Integer[]::new);

    }

    public static void testBuilderOperations() {
        // Build phase
        Stream.Builder<Integer> builder = Stream.builder();
        for (int index = 0; index < 100; ++index) {
            builder.add(index);
        }

        // Build complete
        builder.build(); // No more elements can be added at this point
        builder.add(101); // Will throw an IllegalStateException
    }

    public static void testIterateOperations() {
        // Generate 3 elements and iterate over them
        Stream.of(1, 2, 3).forEach(System.out::println);
    }

    public static void testFilterOperations() {
        // Keep only even numbers
        Stream.of(1, 2, 3, 4, 5).filter(i -> i % 2 == 0).
            forEach(System.out::println);

        // Keep only even numbers
        Stream.iterate(1, i -> ++i).limit(10).filter(i -> i % 2 == 0).
            forEach(System.out::println);

        // Only print 1, 2, and 3
        Stream.of(1, 2, 3, 2, 1).distinct().forEach(System.out::println);
    }

    public static void testMapOperations() {
        // Double each element of the stream Prints 2, 4, 6
        Stream.of(1, 2, 3).map(i -> i * 2).forEach(System.out::println);

        // Convert the stream from integers to strings describing their parity
        // Print: “1: odd”,  “2: even”, “3: odd”
        Stream.of(1, 2, 3).map(i -> i % 2 == 0 ? i + ": even" : i + ": odd").forEach(System.out::println);

        // Iterate from the current integer to 1 for each stream element
        // Print: 3, 2, 1, 2, 1, 1
        Stream.of(3, 2, 1).flatMap(i -> Stream.iterate(i, j -> --j). //Streams can have inner streams!
            limit(i)).forEach(System.out::println);
    }

    public static void testReduceOperations() {
        // Sum integers 1 through 5. Sum will be 15
        int sum = Stream.of(1, 2, 3, 4, 5).reduce(0, (l, r) -> l + r);

        // Sum only even numbers
        sum = Stream.of(1, 3, 5).filter(i -> i % 2 == 0).reduce((l, r) -> l + r).orElse(0);

        // Ensure the sum is a positive number or return 0
        sum = Stream.of(-20, 2, 4, 6).reduce((l, r) -> l + r).filter(i -> i > 0).orElse(0);

        List<String> places = Arrays.asList("Villeray", "Plateau", "Rosemont", "Mile-End");
        boolean isPlaceFound = places.stream().anyMatch("Rosemont"::equals);

        boolean isFound = places.stream().filter("Rosemont"::equals).findFirst().isPresent();

        {
            Map<String, Integer> population = new HashMap<>();
            population.put("Villeray", 145000);
            population.put("Plateau", 100390);
            population.put("Rosemont", 134038);
            population.put("Mile-End", 31910);

            String densePlaces =
                population.keySet().stream().
                    filter(s -> population.getOrDefault(s, 0) > 110000).
                    reduce("", String::concat);
            System.out.println("Dense places: " + densePlaces);
        }

        Map<String, Integer> population = new HashMap<>();
        population.put("Villeray", 145000);
        population.put("Plateau", 100390);
        population.put("Rosemont", 134038);
        population.put("Mile-End", 31910);

        List<String> densePlaces =
            population.keySet().stream().
                filter(s -> population.getOrDefault(s, 0) >= 110000).
                collect(ArrayList::new,                                                 // Supplier
                        (BiConsumer<ArrayList<String>, String>) ArrayList<String>::add, // Accumulator
                        ArrayList<String>::addAll);                                     // Combiner

        densePlaces.forEach(System.out::println);
    }

    public static void testPeekOperations() {
        // Inspect the stream values before and after filter
        Stream.iterate(1, i -> ++i).
            limit(10).
            peek(i -> {
                System.out.println("Before filter: " + i);
            }).
            filter(i -> i % 2 == 0).
            peek(i -> {
                System.out.println("After filter: " + i);
            }).
            forEach(System.out::println);
    }

    public void testStreamBehavior() {
        // Incoherent but harmless stream
        Stream.of(1, 2, 3, 4, 5).peek(System.out::println);

        // findFirst makes this stream coherent
        Stream.of(1, 2, 3, 4, 5).peek(System.out::println).findFirst();

        // Incoherent and harmful stream
        Stream.generate(() -> 1).allMatch(i -> i == 1);

        // No longer harmful and coherent
        Stream.generate(() -> 1).limit(100).allMatch(i -> i == 1);
    }

    public static void testStreamInterference() {
        // Never interfere with the stream sources like this:
        List<String> words =
            new ArrayList<>(
                Arrays.asList("This", "sentence", "contains", "five", "words"));

        words.stream().
            forEach(s -> {
                if (s.equals("five")) words.add("thousand");
            });
    }

    public static void testSpecializedStreams() {
        // Using the all-purpose Stream to count to 10
        Stream.iterate(1, i -> ++i).limit(10).count();

        // Using the specialized IntStream to count to 10
        IntStream.range(1, 10).count();

        // Using the all-purpose Stream to sum
        int sum = Stream.of(1, 2, 3, 4, 5).reduce(0, (l, r) -> l + r);

        // Using the specialized IntStream to sum
        IntStream.range(1, 5).sum();
    }

    public static void printTestStats(int[] classOneScores, int[] classTwoScores) {
        // Convert the arrays into streams to compile statistics
        IntSummaryStatistics classOneStats =
            IntStream.of(classOneScores).summaryStatistics();
        IntSummaryStatistics classTwoStats =
            IntStream.of(classTwoScores).summaryStatistics();


        System.out.println(classOneStats.getMax() + ", " +
                               classOneStats.getMin() + ", " +
                               classOneStats.getAverage() + ", " +
                               classOneStats.getCount());

        System.out.println(classTwoStats.getMax() + ", " +
                               classTwoStats.getMin() + ", " +
                               classTwoStats.getAverage() + ", " +
                               classTwoStats.getCount());

        // Now combine the two
        IntSummaryStatistics combinedStats = new IntSummaryStatistics();
        combinedStats.combine(classOneStats);
        combinedStats.combine(classTwoStats);

        System.out.println(combinedStats.getMax() + ", " +
                               combinedStats.getMin() + ", " +
                               combinedStats.getAverage() + ", " +
                               combinedStats.getCount());
    }

    public static void main(String...args)
    {
        testBuildOperations();
//        testBuilderOperations();
        testIterateOperations();
        testFilterOperations();
        testMapOperations();
        testReduceOperations();
        testPeekOperations();
//        testStreamInterference();
        testSpecializedStreams();

        int[] classOneScores = {90, 87, 65, 88, 76, 75, 99, 92, 82, 71};
        int[] classTwoScores = {77, 81, 97, 80, 63, 64, 55, 90, 89, 78};
        printTestStats(classOneScores, classTwoScores);
    }
}
