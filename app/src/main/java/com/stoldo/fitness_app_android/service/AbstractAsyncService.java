package com.stoldo.fitness_app_android.service;


import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.interfaces.ServiceInterface;


public abstract class AbstractAsyncService implements ServiceInterface {
    @Override
    public void startService() {
        getServiceRunnable().startThread();
    }

    @Override
    public void stopService() {
        getServiceRunnable().stopThread();
    }

    public abstract AbstractBaseRunnable getServiceRunnable();
}
