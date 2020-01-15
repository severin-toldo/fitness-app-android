package com.stoldo.fitness_app_android.service;


import com.stoldo.fitness_app_android.model.abstracts.AbstractAsyncService;
import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.data.events.SoundEvent;
import com.stoldo.fitness_app_android.service.runnables.SoundRunnable;

import java.util.List;

@Singleton
public class SoundService extends AbstractAsyncService<SoundEvent> {
    private SoundRunnable serviceRunnable;

    private SoundService() {}

    @Override
    protected void setServiceRunnable(List<SoundEvent> events) {
        serviceRunnable = new SoundRunnable(events);
    }

    @Override
    protected AbstractBaseRunnable getServiceRunnable() {
        return serviceRunnable;
    }
}
