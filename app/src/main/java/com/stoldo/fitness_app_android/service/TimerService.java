package com.stoldo.fitness_app_android.service;

import com.stoldo.fitness_app_android.model.Observable;
import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.data.events.TimerEvent;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;

import java.util.Arrays;
import java.util.List;

@Singleton
public class TimerService extends AbstractAsyncService<TimerEvent> implements Subscriber<TimerEvent> {
    private TimerRunnable serviceRunnable;
    private TimerEvent lastTimerEvent;

    private TimerService() {}

    @Override
    protected void setServiceRunnable(List<TimerEvent> events) {
        events.forEach(event -> event.addSubscriber(this));
        serviceRunnable = new TimerRunnable(events);
    }

    @Override
    public void update(TimerEvent event) {
        lastTimerEvent = event;
    }

    public void pause() {
        stopService();
    }

    public void resume() {
        // TODO this part event needed?
//        TimerEvent oldData = serviceRunnable.getCurrentData();  // gets old data
//        oldData.setSeconds(lastTimerEvent);
//        data.put("seconds", remainingSeconds); // puts "remembered seconds in"
        startService(Arrays.asList(lastTimerEvent));
    }

    /**
     * data gets passed by the start or stop method in the abstract
     * */
    @Override
    protected AbstractBaseRunnable getServiceRunnable() {
        return serviceRunnable;
    }










    /**
     *
     * TODO javadoc
     * data can hold either seconds as integer or a multiple sequences. (see below)
     *
     * */
    private class TimerRunnable extends AbstractBaseRunnable {
        @lombok.Getter
        private TimerEvent currentEvent; // TODO if above not needed remove this
        private List<TimerEvent> data;
        private Observable observable = new Observable();

        private TimerRunnable(List<TimerEvent> data) {
            super(TimerRunnable.class.getName());
            this.data = data;
        }

        @Override
        public void run() {
            // "while for each" --> should always check if thread is still running
            int index = 0;
            while(index < data.size() && isRunning()) {
                TimerEvent timerEvent = data.get(index);
                currentEvent = timerEvent;
                countDown(timerEvent);
                index++;
            }
        }

        private void countDown(TimerEvent timerEvent) {
            observable.subscribe(timerEvent.getSubscribers());

            Integer seconds = timerEvent.getSeconds();

            while (seconds != 0 && isRunning()) {
                timerEvent.setSeconds(seconds);
                observable.notifySubscribers(timerEvent);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

                seconds--;
            }

            timerEvent.setSeconds(seconds);
            observable.notifySubscribers(timerEvent);
        }
    }
}
