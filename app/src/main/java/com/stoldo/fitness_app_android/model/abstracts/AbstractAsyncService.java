package com.stoldo.fitness_app_android.model.abstracts;


import com.stoldo.fitness_app_android.model.interfaces.Event;

import java.util.List;


public abstract class AbstractAsyncService<E extends Event> {
    public void startService(List<E> events) {
        stopService();
        setServiceRunnable(events);
        getServiceRunnable().startThread();
    }

    public void stopService() {
        // can be null if one class stopService without starting one
        if (getServiceRunnable() != null) {
            getServiceRunnable().stopThread();
        }
    }

    protected abstract AbstractBaseRunnable getServiceRunnable();
    protected abstract void setServiceRunnable(List<E> events);
}
