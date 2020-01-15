package com.stoldo.fitness_app_android.service.runnables;

import com.stoldo.fitness_app_android.model.Observable;
import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.data.events.TimerEvent;

import java.util.List;

/**
 *
 * TODO javadoc
 * data can hold either seconds as integer or a multiple sequences. (see below)
 *
 * */
public class TimerRunnable extends AbstractBaseRunnable {
    @lombok.Getter
    private List<TimerEvent> data;
    private Observable observable = new Observable<TimerEvent>();

    public TimerRunnable(List<TimerEvent> data) {
        super(TimerRunnable.class.getName());
        this.data = data;
    }

    @Override
    public void run() {
        // "while for each" --> should always check if thread is still running
        int index = 0;
        while(index < data.size() && isRunning()) {
            TimerEvent timerEvent = data.get(index);
            countDown(timerEvent);
            index++;
        }
    }

    private void countDown(TimerEvent timerEvent) {
        observable.subscribe(timerEvent.getSubscribers());

        Integer seconds = timerEvent.getSeconds();

        // TODO maybe with do while or duble to save halfe, 0.2, 0.3 etc seconds
        while (seconds != 0 && isRunning()) {
            timerEvent.setSeconds(seconds);
            observable.notifySubscribers(timerEvent);

            try {
                Thread.sleep(1000);
            } catch (Exception e) {}

            seconds--;
        }

        timerEvent.setSeconds(seconds);
        observable.notifySubscribers(timerEvent);
    }
}
