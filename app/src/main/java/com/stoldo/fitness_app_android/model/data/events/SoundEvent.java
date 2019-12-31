package com.stoldo.fitness_app_android.model.data.events;

import android.content.Context;

import androidx.annotation.RawRes;

import com.stoldo.fitness_app_android.model.interfaces.Event;

@lombok.Setter
@lombok.Getter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class SoundEvent implements Event {
    private Context applicationContext; // (getApplicationContext() or getActivity().getApplicationContext() usually
    private @RawRes int fileRef; // Res.raw.<<File>>
}
