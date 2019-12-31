package com.stoldo.fitness_app_android.service;


import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.interfaces.Event;
import com.stoldo.fitness_app_android.model.interfaces.Service;

import java.util.List;


public abstract class AbstractAsyncService<E extends Event> implements Service<E> {
    @Override
    public void startService(List<E> events) {
        stopService();
        setServiceRunnable(events);
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
    protected abstract void setServiceRunnable(List<E> events);
}
