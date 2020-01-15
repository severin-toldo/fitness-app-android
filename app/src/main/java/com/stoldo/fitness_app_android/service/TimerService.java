package com.stoldo.fitness_app_android.service;


import com.stoldo.fitness_app_android.model.abstracts.AbstractAsyncService;
import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.data.events.TimerEvent;
import com.stoldo.fitness_app_android.service.runnables.TimerRunnable;

import java.util.List;

@Singleton
public class TimerService extends AbstractAsyncService<TimerEvent> {
    private TimerRunnable serviceRunnable;

    private TimerService() {}

    public void pause() {
        stopService();
    }

    public void resume() {
        startService(serviceRunnable.getData());
    }

    @Override
    protected void setServiceRunnable(List<TimerEvent> events) {
        serviceRunnable = new TimerRunnable(events);
    }

    @Override
    protected AbstractBaseRunnable getServiceRunnable() {
        return serviceRunnable;
    }
}
