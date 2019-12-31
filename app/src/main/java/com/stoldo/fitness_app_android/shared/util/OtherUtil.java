package com.stoldo.fitness_app_android.shared.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.stoldo.fitness_app_android.service.SingletonService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class OtherUtil {
    public static int convertDpToPixel(int dp, Context context){
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }

    // TODO unify
    public static void runSetter(Field field, Object o, Object value) {
        // MZ: Find the correct method
        for (Method method : o.getClass().getMethods()) {
            if ((method.getName().startsWith("set")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    // MZ: Method found, run it
                    try {
                        method.invoke(o, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        Log.e("OtherUtil", "Could not determine method: " + method.getName());
                    }

                }
            }
        }
    }

    // TODO unify
    public static Object runGetter(Field field, Object o) {
        // MZ: Find the correct method
        for (Method method : o.getClass().getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    // MZ: Method found, run it
                    try {
                        return method.invoke(o);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        Log.e("OtherUtil", "Could not determine method: " + method.getName());
                    }

                }
            }
        }

        return null;
    }

    public static boolean isValidIndex(int index, int listSize) {
        return index >= 0 && index < listSize;
    }

    // TODO implement in savable
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static Object getService(Class serviceClass) {
        return SingletonService.getInstance(null).getSingletonByClass(serviceClass);
    }

    /**
     * Throws passed exception if passed boolean is false
     *
     * @param b condition, if false error is thrown
     * @param e exception which should be thrown
     * */
    public static void falseThenThrow(boolean b, RuntimeException e) throws RuntimeException {
        if (!b) {
            throw e; // TODO logging
        }
    }

    /**
     * Changes the color of the status bar to the passed color
     *
     * @param color color to which the status bar color should be changed
     * @param activity calling activity
     * */
    public static void changeStatusbarColor(Activity activity, @ColorRes int color) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(activity, color));
    }
}
