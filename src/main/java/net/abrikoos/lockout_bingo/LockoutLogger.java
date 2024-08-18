package net.abrikoos.lockout_bingo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockoutLogger {
    private static Logger LOGGER;
    private static LockoutLogger instance;


    private LockoutLogger() {
        LOGGER = LoggerFactory.getLogger("lockout-bingo");
    }


    public static void log(String message) {

        getInstance();
        LOGGER.info(message);
    }

    private static synchronized void getInstance() {
        if (instance == null) {
            instance = new LockoutLogger();
        }
    }


}
