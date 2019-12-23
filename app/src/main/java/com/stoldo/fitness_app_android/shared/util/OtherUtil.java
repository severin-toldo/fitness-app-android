package com.stoldo.fitness_app_android.shared.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.stoldo.fitness_app_android.service.SingletonService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public static boolean isValidIndex(int index, int listSize) {
        return index >= 0 && index < listSize;
    }

    // TODO impment in savable
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static Object getService(Class serviceClass) {
        return SingletonService.getInstance(null).getSingletonByClass(serviceClass);
    }
}
