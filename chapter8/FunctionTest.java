package chapter8;

import java.util.Arrays;
import java.util.function.Function;

public class FunctionTest {

    public static Function<String, Function<Integer, String>> getLanguageSpellChecker() {

        return language -> numberedWord ->
        {
            switch (language) {
                case "English": {
                    return Arrays.asList("Zero", "One", "Two", "Three", "Four",
                                         "Five", "Six", "Seven", "Eight", "Nine").
                        get(numberedWord % 10);
                }

                case "French": {
                    return Arrays.asList("Zéro", "Un", "Deux", "Trois", "Quatre",
                                         "Cinq", "Six", "Sept", "Huit", "Neuf").
                        get(numberedWord % 10);
                }

                case "Italian": {
                    return Arrays.asList("Zero", "Uno", "Due", "Tre", "Quatro",
                                         "Cinque", "Sei", "Sette", "Otto", "Nove").
                        get(numberedWord % 10);
                }

                default: {
                    return "???";
                }
            }
        };
    }


    public static void main(String...args)
    {
        // Test currying
        Function<String, Function<Integer, String>> checker = getLanguageSpellChecker();
        System.out.println(checker.apply("English").apply(2));
        System.out.println(checker.apply("French").apply(8));
        System.out.println(checker.apply("Italian").apply(7));
        System.out.println(checker.apply("Klingon").apply(9));
    }


}
