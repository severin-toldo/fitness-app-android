package com.stoldo.fitness_app_android.model;

import com.stoldo.fitness_app_android.model.interfaces.Event;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Extended or used by classes which can be observed.
 * */
public class Observable<E extends Event> {
    private List<Subscriber<E>> subscribers = new ArrayList<>();

    public void subscribe(Subscriber<E> subscriber) {
        if (!hasAlreadySubscribed(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    public boolean hasAlreadySubscribed(Subscriber<E> subscriber) {
        return subscribers.contains(subscriber);
    }

    public void unsubscribe(Subscriber<E> subscriber) {
        subscribers.remove(subscriber);
    }

    public void subscribe(List<Subscriber<E>> subscribers) {
        subscribers.forEach(subscriber -> subscribe(subscriber));
    }

    public void unsubscribe(List<Subscriber<E>> subscribers) {
        subscribers.forEach(subscriber -> unsubscribe(subscriber));
    }

    public void notifySubscribers(E event) {
        subscribers.forEach(subscriber -> subscriber.update(event));
    }
}
