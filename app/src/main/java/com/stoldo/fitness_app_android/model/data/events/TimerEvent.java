package com.stoldo.fitness_app_android.model.data.events;

import com.stoldo.fitness_app_android.model.enums.TimeType;
import com.stoldo.fitness_app_android.model.interfaces.Event;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;

import java.util.ArrayList;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class TimerEvent implements Event {
    private Integer seconds;
    private TimeType timeType;
    private List<Subscriber<TimerEvent>> subscribers = new ArrayList<>();

    public TimerEvent(Integer seconds, TimeType timeType, List<Subscriber<TimerEvent>> subscribers) {
        this.seconds = seconds;
        this.timeType = timeType;
        this.subscribers = subscribers;
    }
}
