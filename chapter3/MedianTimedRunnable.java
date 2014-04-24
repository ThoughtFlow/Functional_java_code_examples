package chapter3;

import java.util.ArrayList;
import java.util.Collections;

@FunctionalInterface
public interface MedianTimedRunnable extends FunctionalTimedRunnable {

    default long runTimed() throws InterruptedException {
        printInterfaceName("MedianTimedRunnable ");
        final int sampleSize = 5;
        ArrayList<Long> longs = new ArrayList<>(sampleSize);

        for (int count = 0; count < sampleSize; ++count) {
            longs.add(
                FunctionalTimedRunnable.super.runTimed());
        }

        Collections.sort(longs);
        return longs.get(sampleSize / 2);
    }
}