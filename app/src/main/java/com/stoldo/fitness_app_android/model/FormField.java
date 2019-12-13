package com.stoldo.fitness_app_android.model;

import com.stoldo.fitness_app_android.model.enums.FormFieldType;

import lombok.AccessLevel;
import lombok.Getter;

// TODO DO NOt USE remove is just a left over!
@lombok.Getter
@lombok.Setter
public class FormField<T> {
    private FormFieldType type;
    private T data;

    @Getter(AccessLevel.NONE)
    private String label;

//    public String getLabel(String propertyName, AppCompatActivity activity) {
//        int resId = activity.getResources().getIdentifier(propertyName, "string", activity.getPackageName());
//        return activity.getString(resId);
//    }
}
