package chapter5;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class ConsumerComposition {

    private static void logComposition() {
        Logger logger = Logger.getLogger("ConsumerComposition");

        Consumer<String> printToOut = System.out::println;
        Consumer<String> printToLog = s -> {
            if (s.contains("critical")) {
                logger.severe(s);
            }
            else {
                logger.info(s);
            }
        };

        Consumer<String> superPrinter = printToOut.andThen(printToLog);

        superPrinter.accept("Hello");
        superPrinter.accept("This is critical");
    }

    public static void main(String... args) {
        logComposition();
    }
}
