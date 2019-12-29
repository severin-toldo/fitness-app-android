package com.stoldo.fitness_app_android.model.interfaces;


public interface Subscriber<E extends Event> {
    public void update(E event);
}
