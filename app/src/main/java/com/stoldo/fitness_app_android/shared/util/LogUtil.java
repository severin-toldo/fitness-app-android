package com.stoldo.fitness_app_android.shared.util;

import android.util.Log;

/**
 * Class to facilitate the logging of Messages, Errors, Infos etc.
 * */
public class LogUtil {
    /**
     * Logs a given Message as error and exits the Programm. Used when a error is fatal and the programm should not continue (ex. Service setup fails)
     *
     * @param message Message to log
     * @param clazz Class from which the method is called
     * @param e Exception to log
     * */
    public static void logErrorAndExit(String message, Class clazz, Exception e) {
        logError(message, clazz, e);
        System.exit(1);
    }

    /**
     * Logs a given Message as error and Prints the Stacktrace
     *
     * @param message Message to log
     * @param clazz Class from which the method is called
     * @param e Exception to log
     * */
    public static void logError(String message, Class clazz, Exception e) {
        Log.e(clazz.getName(), message + " Message: " + e.getMessage());
        e.printStackTrace();
        logMyDebug(clazz, message);
    }


    /**
     * Logs a given Message as info
     *
     * @param message Message to log
     * @param clazz Class from which the method is called
     * */
    public static void logInfo(String message, Class clazz) {
        Log.i(clazz.getName(), message);
        logMyDebug(clazz, message);
    }

    /**
     * Logs a given message with the MYDEBUG tag
     *
     * @param clazz Class from which the method is called
     * @param message Message to log
     * */
    public static void logMyDebug(Class clazz, String message) {
        Log.d("MYDEBUG", "[" + clazz.getName() + "]: " + message);
    }
}
