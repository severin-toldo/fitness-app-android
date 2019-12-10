package com.stoldo.fitness_app_android.model;

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

    public void notifySubscribers(String action) {
        subscribers.forEach(subscriber -> subscriber.update(action));
    }
}
