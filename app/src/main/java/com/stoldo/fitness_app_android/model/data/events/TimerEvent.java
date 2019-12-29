package com.stoldo.fitness_app_android.model.data.events;

import com.stoldo.fitness_app_android.model.enums.TimeType;
import com.stoldo.fitness_app_android.model.interfaces.Event;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;

import java.util.ArrayList;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class TimerEvent implements Event {
    private Integer seconds;
    private TimeType timeType;
    private List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }
}
