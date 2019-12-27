package com.stoldo.fitness_app_android.service;


import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.interfaces.ServiceInterface;

import java.util.Map;


public abstract class AbstractAsyncService implements ServiceInterface {
    @Override
    public void startService(Map<String, Object> data) {
        stopService();
        setServiceRunnable(data);
        getServiceRunnable().startThread();
    }

    @Override
    public void stopService() {
        // can be null if one class stopService without starting one
        if (getServiceRunnable() != null) {
            getServiceRunnable().stopThread();
        }
    }

    protected abstract AbstractBaseRunnable getServiceRunnable();
    protected abstract void setServiceRunnable(Map<String, Object> data);
}
