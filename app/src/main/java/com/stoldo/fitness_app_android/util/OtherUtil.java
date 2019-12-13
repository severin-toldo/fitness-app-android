package com.stoldo.fitness_app_android.util;

import java.util.HashMap;
import java.util.Map;

public class OtherUtil {
    public static Map<String, Object> getEditMenuEventMap(String actionValue, Boolean editModeValue) {
        Map<String, Object> data = new HashMap<>();
        data.put("action", actionValue);
        data.put("editMode", editModeValue);
        return data;
    }
}
