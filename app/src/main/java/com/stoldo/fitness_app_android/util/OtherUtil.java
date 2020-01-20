package com.stoldo.fitness_app_android.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.j256.ormlite.field.DatabaseField;
import com.stoldo.fitness_app_android.service.SingletonService;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class that holds different kind of methods to facilitate different processed throughout the whole application.
 * A general Util class.
 * */
public class OtherUtil {

    /**
     * Converts from the device independent dp value to pixel
     * @param dp Density-independent Pixels
     * @param context
     * @return pixel value
     */
    public static int convertDpToPixel(int dp, Context context){
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }

    /**
     * Gets the method in the spedified class with the given field and method name beginning
     * @param start the beginning of the Methods name
     * @param field the field which name should be in the method
     * @param o the object in which the method should be
     * @return the method
     */
    public static Method getMethod(String start, Field field, Object o){
        // MZ: Find the correct method
        for (Method method : o.getClass().getMethods()) {
            if ((method.getName().startsWith(start)) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    // MZ: Method found, run it
                        return method;
                }
            }
        }
        return null;
    }

    /**
     * Sets a value to the field by the setter
     * @param field the field which name should be in the method
     * @param o the object in which the method should be
     * @param value the new value for the field
     */
    public static void runSetter(Field field, Object o, Object value) {
        Method setter = getMethod("set", field, o);
        try {
            Class parType = field.getType();
            if (value.getClass() != parType) {
                if (StringUtils.isEmpty(value.toString())) {
                    //TODO: könnte besser sein
                    if (parType == Integer.class) {
                        value = 0;
                    }
                    //else....
                } else {
                    Constructor convertConstructor = parType.getConstructor(String.class);
                    value = convertConstructor.newInstance(value);
                }

            }

            setter.invoke(o, value);

        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.e("OtherUtil", "Could not determine method: " + setter.getName());
        } catch (NoSuchMethodException e) {
            Log.e("OtherUtil", "Could not determine constructor of target Class to Convert: " + setter.getName());
        } catch (InstantiationException e) {
            Log.e("OtherUtil", "target Class has no Constructor to Convert: " + setter.getName());
        }
    }

    /**
     * Gets the value of the field by its getter
     * @param field the field which name should be in the method
     * @param o the object in which the method should be
     * @return the value of the field
     */
    public static Object runGetter(Field field, Object o) {
        Method getter = getMethod("get", field, o);
        try {
            if (getter != null) {
                return getter.invoke(o);
            }
        } catch (IllegalAccessException e) {
            Log.e("OtherUtil", "Could not determine getter method: " + getter.getName());
        } catch (InvocationTargetException e) {
            Log.e("OtherUtil", "Could not invoke method: " + getter.getName());
        }

        return null;
    }

    /**
     * Checks if a given index is valid with a given list size.
     *
     * @param index index to check
     * @param listSize list size of the used list
     * @return true if the index is valid, false if the index is invalid
     * */
    public static boolean isValidIndex(int index, int listSize) {
        return index >= 0 && index < listSize;
    }

    /**
     * Returns the instance of the desired singleton.
     *
     * @param singletonClass Class of the desired singleton
     * @return Instance of the desired singleton
     * */
    public static Object getSingletonInstance(Class singletonClass) {
        return SingletonService.getInstance(null).getSingletonByClass(singletonClass);
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

    /**
     * Pops a toaster in the given activity with the given message.
     *
     * @param activity Activity in which to pop the toaster
     * @param message Message to display
     * */
    public static void popToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    // TODO unit tests --> Stefano
    /**
     * says if the field in the database can be null
     * @param field the field to be checked
     * @return if the field can be null
     */
    public static boolean canBeNull(Field field){
        if(field != null){
            DatabaseField dBField = field.getAnnotation(DatabaseField.class);
            return dBField.canBeNull();
        }

        return false;
    }

    /**
     * Hides the keyboard
     * @param activity the activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
