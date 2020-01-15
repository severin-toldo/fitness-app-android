package com.stoldo.fitness_app_android.service.runnables;

import android.media.MediaPlayer;

import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRunnable;
import com.stoldo.fitness_app_android.model.data.events.SoundEvent;

import java.util.List;

/**
 *
 * TODO javadoc
 * data can hold either seconds as integer or a multiple sequences. (see below)
 *
 * */
public class SoundRunnable extends AbstractBaseRunnable {
    @lombok.Getter
    private List<SoundEvent> data;
    private MediaPlayer mp;

    public SoundRunnable(List<SoundEvent> data) {
        super(SoundRunnable.class.getName());
        this.data = data;
    }

    @Override
    public void run() {
        // "while for each" --> should always check if thread is still running
        int index = 0;
        while(index < data.size() && isRunning()) {
            SoundEvent soundEvent = data.get(index);
            playSingleSound(soundEvent);
            index++;
        }
    }

    private void playSingleSound(SoundEvent soundEvent) {
        mp = MediaPlayer.create(soundEvent.getApplicationContext(), soundEvent.getFileRef());
        mp.start();

        while (mp.isPlaying()) {} // keep code occupied while file is playing

        mp.stop();
    }
}