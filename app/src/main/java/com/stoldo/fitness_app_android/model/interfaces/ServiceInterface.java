package com.stoldo.fitness_app_android.model.interfaces;

import java.util.Map;

public interface ServiceInterface {
    public void startService(Map<String, Object> data);

    public void stopService();
}
