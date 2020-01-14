package com.stoldo.fitness_app_android.service;


import com.stoldo.fitness_app_android.model.Observable;
import com.stoldo.fitness_app_android.model.abstracts.AbstractAsyncService;
import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.data.events.TimerEvent;

import java.util.List;

@Singleton
public class TimerService extends AbstractAsyncService<TimerEvent> {
    private TimerRunnable serviceRunnable;

    private TimerService() {}

    public void pause() {
        stopService();
    }

    public void resume() {
        startService(serviceRunnable.getData());
    }

    @Override
    protected void setServiceRunnable(List<TimerEvent> events) {
        serviceRunnable = new TimerRunnable(events);
    }

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
        private List<TimerEvent> data;
        private Observable observable = new Observable<TimerEvent>();

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
}
