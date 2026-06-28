package net.abrikoos.blockout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockoutLogger {
    private static Logger LOGGER;
    private static BlockoutLogger instance;


    private BlockoutLogger() {
        LOGGER = LoggerFactory.getLogger("blockout");
    }


    public static void log(String message) {

        getInstance();
        LOGGER.info(message);
    }

    private static synchronized void getInstance() {
        if (instance == null) {
            instance = new BlockoutLogger();
        }
    }


}
