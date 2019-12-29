package com.stoldo.fitness_app_android.model;

import com.stoldo.fitness_app_android.model.interfaces.Event;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void subscribe(List<Subscriber> subscribers) {
        subscribers.forEach(subscriber -> subscribe(subscriber));
    }

    public void unsubscribe(List<Subscriber> subscribers) {
        subscribers.forEach(subscriber -> unsubscribe(subscriber));
    }

    public void notifySubscribers(Event event) {
        subscribers.forEach(subscriber -> subscriber.update(event));
    }
}
