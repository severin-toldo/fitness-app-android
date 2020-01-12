package com.stoldo.fitness_app_android.model.enums;

import android.view.View;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FormFieldType {
    TEXTFIELD(null),
    NUMBERFIELD(null),
    DROPDOWN(null),
    TEXTAREA(null),
    IMAGE(null);

    @Getter
    private View view;

    // TODO implement views
}
