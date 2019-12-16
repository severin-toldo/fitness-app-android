package com.stoldo.fitness_app_android.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.util.HashMap;
import java.util.Map;

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
}
