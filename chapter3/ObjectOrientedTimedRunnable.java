package chapter3;

public abstract class ObjectOrientedTimedRunnable implements Runnable {
    private long executionDuration;

    @Override
    public abstract void run();

    public final void runTimed() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        Thread thread = new Thread(this);
        thread.start();
        thread.join();

        executionDuration = System.currentTimeMillis() - startTime;
    }

    public final long getExecutionDuration() {
        return executionDuration;
    }

    public static void main(String... args) throws InterruptedException
    {
        ObjectOrientedTimedRunnable runner =
            new ObjectOrientedTimedRunnable()
            {
                @Override
                public void run()
                {
                    // do something you want to time!
                }
            };

        runner.runTimed();
        System.out.println("Time: " + runner.getExecutionDuration());
    }
}
