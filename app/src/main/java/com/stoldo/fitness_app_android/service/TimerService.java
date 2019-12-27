package com.stoldo.fitness_app_android.service;

import com.stoldo.fitness_app_android.model.Observable;
import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import java.util.Arrays;
import java.util.Map;

@Singleton
public class TimerService extends AbstractAsyncService implements Subscriber {
    private TimerRunnable serviceRunnable;
    private Integer remainingSeconds;

    private TimerService() {}

    @Override
    protected void setServiceRunnable(Map<String, Object> data) {
        data.put("service", this);
        serviceRunnable = new TimerRunnable(data);
    }

    @Override
    public void update(Map<String, Object> data) {
        remainingSeconds = (Integer) data.get("remainingSeconds");
    }

    public void pause() {
        stopService();
    }

    public void resume() {
        Map<String, Object> data = serviceRunnable.getData();  // gets old data
        data.put("seconds", remainingSeconds); // puts "remembered seconds in"
        startService(data);
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
        private Map<String, Object> data;
        private Observable observable = new Observable();

        private TimerRunnable(Map<String, Object> data) {
            super(TimerRunnable.class.getName());
            this.data = data;
        }

        @Override
        public void run() {
            observable.subscribe((Subscriber) data.get("subscriber"));
            observable.subscribe((Subscriber) data.get("service"));
            Integer seconds = (Integer) data.get("seconds");

            while (seconds != 0 && isRunning()) {
                observable.notifySubscribers(OtherUtil.getEventMap(Arrays.asList("remainingSeconds"), Arrays.asList(seconds)));

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

                seconds--;
            }

            observable.notifySubscribers(OtherUtil.getEventMap(Arrays.asList("remainingSeconds"), Arrays.asList(seconds)));
        }

        // TODO implement
        private void countDown(Integer seconds) {

        }
    }
}
