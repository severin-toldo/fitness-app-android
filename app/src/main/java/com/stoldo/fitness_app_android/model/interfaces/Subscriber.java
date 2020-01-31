package com.stoldo.fitness_app_android.model.interfaces;

/**
 * implemented by classes which subscribe to an observable
 * */
public interface Subscriber<E extends Event> {
    public void update(E event);
}
