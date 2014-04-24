package chapter3;

public abstract class StatelessTimedRunnable implements Runnable {
    public abstract void run();

    public long runTimed() throws InterruptedException {
        System.out.println("Running StatelessTimedRunnable");
        long startTime = System.currentTimeMillis();

        Thread thread = new Thread(this);
        thread.start();
        thread.join();

        return System.currentTimeMillis() - startTime;
    }
}

