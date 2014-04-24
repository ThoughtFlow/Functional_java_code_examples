package chapter3;

@FunctionalInterface
public interface AverageTimedRunnable extends FunctionalTimedRunnable {

    default long runTimed() throws InterruptedException {
        printInterfaceName("AverageTimedRunnable");
        long timeSum = 0;

        for (int count = 0; count < 5; ++count) {
            timeSum +=
                FunctionalTimedRunnable.super.runTimed();
        }

        return Math.round(timeSum / 5);
    }
}