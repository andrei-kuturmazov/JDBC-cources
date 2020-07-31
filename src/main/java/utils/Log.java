package utils;

import org.apache.log4j.Logger;

public class Log {
    private static final Logger log = Logger.getLogger(Log.class);

    public static void trace(String message) {
        log.trace(message);
    }
    public static void trace(String message, Throwable e) {
        log.trace(message, e);
    }
    public static void debug(String message) {
        log.debug(message);
    }
    public static void debug(String message, Throwable e) {
        log.trace(message, e);
    }
    public static void info(String message) {
        log.info(message);
    }
    public static void info(String message, Throwable e) {
        log.info(message, e);
    }
    public static void warn(String message) {
        log.warn(message);
    }
    public static void warn(String message, Throwable e) {
        log.warn(message, e);
    }
    public static void error(String message) {
        log.error(message);
    }
    public static void error(String message, Throwable e) {
        log.error(message, e);
        throw new AssertionError(e);
    }
    public static void error(Exception e) {
        log.error("Exception occurs: ", e);
        throw new AssertionError(e);
    }
}
