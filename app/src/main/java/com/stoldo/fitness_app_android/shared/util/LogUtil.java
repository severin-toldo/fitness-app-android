package com.stoldo.fitness_app_android.shared.util;

import android.util.Log;

public class LogUtil {
    public static void logErrorAndExit(String message, Class clazz, Exception e) {
        logError(message, clazz, e);
        System.exit(1);
    }

    public static void logError(String message, Class clazz, Exception e) {
        Log.e(clazz.getName(), message + " Message: " + e.getMessage());
        e.printStackTrace();
        logMyDebug(clazz, message);
    }

    public static void logInfo(String message, Class clazz) {
        Log.i(clazz.getName(), message);
        logMyDebug(clazz, message);
    }

    public static void logMyDebug(Class clazz, String message) {
        Log.d("MYDEBUG", "[" + clazz.getName() + "]: " + message);
    }
}
