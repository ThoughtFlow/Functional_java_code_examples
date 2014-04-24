package chapter8;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerTest {

    private String state = "My state";

    public String getState()
    {
        return state;
    }

    public static void main(String...args)
    {
        Logger logger = Logger.getLogger("LoggerTest");
        LoggerTest myObject = new LoggerTest();

        // This is how we use log guards pre Java-8
        if (logger.isLoggable(Level.FINE))
        {
            logger.fine("My debug log: " + myObject.getState());
        }

        // Using suppliers with Java 8
        logger.fine(() -> "My debug log: " + myObject.getState());
    }
}
