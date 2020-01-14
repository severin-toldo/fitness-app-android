package com.stoldo.fitness_app_android.model.enums;

import androidx.annotation.ColorRes;

import com.stoldo.fitness_app_android.R;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * determines what kind of time it is.
 * */
@AllArgsConstructor
public enum TimeType {
    PREPARE(R.color.green),
    WORK(R.color.red),
    REST(R.color.colorPrimary);

    @Getter
    private @ColorRes int backgroundColor;
}
