package chapter5;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class SpliteratorTest {
    public static boolean isMovieInList(String title, List<String> movieList) throws InterruptedException {
        // Obtain a spliterator from the movie list
        Spliterator<String> s1 = movieList.spliterator();

        // Split the original list in half.
        // Now s1 and s2 each contain half the list.
        Spliterator<String> s2 = s1.trySplit();

        BooleanHolder booleanHolder = new BooleanHolder();
        if (s2 != null) {
            Consumer<String> finder = movie -> {
                if (movie.equals(title)) booleanHolder.isFound = true;
            };
            Thread t1 = new Thread(() -> s1.forEachRemaining(finder));
            Thread t2 = new Thread(() -> s2.forEachRemaining(finder));

            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }

        return booleanHolder.isFound;
    }

    private static class BooleanHolder {
        public boolean isFound = false;
    }

    public static void main(String... args) throws InterruptedException {

        // Generate 100K movie titles using integers as names
        List<String> movies = new ArrayList<>(100000);
        for (int index = 0; index < 100000; ++index) {
            movies.add(String.valueOf(index));
        }

        System.out.println(isMovieInList("777", movies));
    }
}
