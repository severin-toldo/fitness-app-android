package com.stoldo.fitness_app_android.model.enums;

import androidx.annotation.ColorRes;

import com.stoldo.fitness_app_android.R;

/**
 * determines what kind of time it is.
 * */
public enum TimeType {
    PREPARE(R.color.green),
    WORK(R.color.red),
    REST(R.color.colorPrimary);

    private @ColorRes int backgroundColor;

    TimeType(@ColorRes int color) {
        this.backgroundColor = color;
    }

    public @ColorRes int getBackgroundColor() {
        return backgroundColor;
    }
}
