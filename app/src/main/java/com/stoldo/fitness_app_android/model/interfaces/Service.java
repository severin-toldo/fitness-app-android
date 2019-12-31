package com.stoldo.fitness_app_android.model.interfaces;


import java.util.List;

public interface Service<E extends Event> {
    public void startService(List<E> events);

    public void stopService();
}
