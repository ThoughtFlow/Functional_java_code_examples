package chapter5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapTesting {

    private Map<Integer, List<String>> movieDatabase = new HashMap<>();

    private void addMovie(Integer year, String title)
    {
        movieDatabase.computeIfAbsent(year, k -> new LinkedList<>());
        movieDatabase.compute(year,
                              (k, v) -> {
                                  // K is the key of the map (the year)
                                  // V is the value containing the list strings (titles)
                                  v.add(title);
                                  return v;
                              });
    }

    private void addMovies(Integer year, List<String> titles)
    {
        // Merge the contents of the current list at key=year with titles
        movieDatabase.merge(year, titles,
                            // BiFunction to append t2 to t1
                            (t1, t2) -> {
                                t1.addAll(t2);
                                return t1;
                            });
    }

    private void printMovieDatabase()
    {
        movieDatabase.forEach((k, v) -> System.out.println("Year: " + k + " Movie: " + v));
    }

    public static void main(String...args)
    {
        MapTesting mapTesting = new MapTesting();
        List<String> titles =
            new ArrayList<>(Arrays.asList("Meet the Baron", "Nertsery Rhymes"));

        mapTesting.addMovie(1934, "Fugitive Lovers");
        mapTesting.addMovie(1938, "Start Cheering");
        mapTesting.addMovie(1938, "Rockin' in the Rockies");
        mapTesting.addMovies(1933, titles);
        mapTesting.printMovieDatabase();
    }
}
