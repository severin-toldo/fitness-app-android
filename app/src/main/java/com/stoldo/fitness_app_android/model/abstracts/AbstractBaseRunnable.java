package com.stoldo.fitness_app_android.model.abstracts;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractBaseRunnable implements Runnable {
    private Thread thread;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private String threadName;

    public AbstractBaseRunnable(String threadName) {
        this.threadName = threadName;
    }

    public void startThread() {
        thread = new Thread(this);
        thread.setName(threadName);
        isRunning.set(true);
        thread.start();
    }

    public void stopThread() {
        onStop();
        isRunning.set(false);

        // interrupts thread if the normal stop doesn't work (long sleep for example)
        if (isAlive()) {
            thread.interrupt();
        }
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public boolean isAlive() {
        return thread.isAlive();
    }

    protected void onStop() {}
}
