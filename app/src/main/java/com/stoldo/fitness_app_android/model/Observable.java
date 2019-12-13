package com.stoldo.fitness_app_android.model;

import com.stoldo.fitness_app_android.model.interfaces.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Observable {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(Map<String, Object> data) {
        subscribers.forEach(subscriber -> subscriber.update(data));
    }
}
