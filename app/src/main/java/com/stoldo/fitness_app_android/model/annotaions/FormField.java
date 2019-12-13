package com.stoldo.fitness_app_android.model.annotaions;

import com.stoldo.fitness_app_android.model.enums.FormFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormField {
    FormFieldType type();
}
