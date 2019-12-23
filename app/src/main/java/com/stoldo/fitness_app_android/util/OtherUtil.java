package com.stoldo.fitness_app_android.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class OtherUtil {
    public static Map<String, Object> getEditMenuEventMap(String actionValue, Boolean editModeValue) {
        Map<String, Object> data = new HashMap<>();
        data.put("action", actionValue);
        data.put("editMode", editModeValue);
        return data;
    }

    public static int convertDpToPixel(int dp, Context context){
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }

    public static Object runGetter(Field field, Object o, Object value)
    {
        // MZ: Find the correct method
        for (Method method : o.getClass().getMethods())
        {
            if ((method.getName().startsWith("set")) && (method.getName().length() == (field.getName().length() + 3)))
            {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase()))
                {
                    // MZ: Method found, run it
                    try
                    {
                        return method.invoke(o, value);
                    }
                    catch (IllegalAccessException e)
                    {
                        Log.e("OtherUtil","Could not determine method: " + method.getName());
                    }
                    catch (InvocationTargetException e)
                    {
                        Log.e("OtherUtil", "Could not determine method: " + method.getName());
                    }

                }
            }
        }


        return null;
    }
}
