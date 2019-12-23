package com.stoldo.fitness_app_android.model.abstracts;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractBaseRunnable implements Runnable {
    private Thread thread;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private String threadName;

    public AbstractBaseRunnable(String threadName) {
        this.threadName = threadName;
    }

    public void startThread() {
        thread = new Thread(this);
        thread.setName(threadName);
        thread.start();
    }

    public void stopThread() {
        running.set(false);

        // interrupts thread if the normal stop doesn't work (long sleep for example)
        if (thread.isAlive()) {
            thread.interrupt();
        }
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            execute();
        }
    }

    // TODO maybe remove
    public boolean isRunning() {
        return running.get();
    }

    // TODO maybe remove
    public boolean isAlive() {
        return thread.isAlive();
    }

    public abstract void execute();
}
