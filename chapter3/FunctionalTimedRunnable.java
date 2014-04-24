package chapter3;

@FunctionalInterface
public interface FunctionalTimedRunnable extends Runnable {

    // Wow! Interfaces can now define behavior!
    default long runTimed() throws InterruptedException {
        printInterfaceName("FunctionalTimedRunnable");
        long startTime = System.currentTimeMillis();

        Thread thread = new Thread(this);
        thread.start();
        thread.join();
        return System.currentTimeMillis() - startTime;
    }

    default void launchTest() throws InterruptedException {
        System.out.println("This thread executed for: " + runTimed() + " ms.");
    }

    default void printInterfaceName(String interfaceName) {
        System.out.println("Running " + interfaceName);
    }

    public static void testFunctionalTimedRunnable() throws InterruptedException {
        FunctionalTimedRunnable timedRunnable = () -> {/* Do something*/};
        timedRunnable.launchTest();
    }

    public static void main(String...args) throws InterruptedException
    {
        FunctionalTimedRunnable.testFunctionalTimedRunnable();
    }
}