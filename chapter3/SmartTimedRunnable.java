package chapter3;

@FunctionalInterface
public interface SmartTimedRunnable extends AverageTimedRunnable, MedianTimedRunnable {

    public default long runTimed() throws InterruptedException {
        printInterfaceName("SmartTimedRunnable");
        long averageElapsedTime =
            AverageTimedRunnable.super.runTimed();

        // Choose average time if less than 1 second
        return averageElapsedTime < 1000 ?
            averageElapsedTime :
            MedianTimedRunnable.super.runTimed();
    }

    public static void main(String...args) throws InterruptedException
    {
        SmartTimedRunnable runnable = () -> {/* Do something */};
        runnable.runTimed();
    }
}
