package chapter3;

@FunctionalInterface
public interface StaticTimedRunnable extends Runnable {
    static long runTimed(StaticTimedRunnable timedRunnable)
        throws InterruptedException {
        printInterfaceName("StaticTimedRunnable");
        long startTime = System.currentTimeMillis();

        Thread thread = new Thread(timedRunnable);
        thread.start();
        thread.join();
        return System.currentTimeMillis() - startTime;
    }

    static void launchTest(StaticTimedRunnable runnable) throws InterruptedException {
        System.out.println("This thread executed for: " + runTimed(runnable) + " ms.");
    }

    static void printInterfaceName(String interfaceName) {
        System.out.println("Running " + interfaceName);
    }

    public static void main(String...args) throws InterruptedException
    {
        StaticTimedRunnable.launchTest(() -> {/* do something */ });
    }
}
