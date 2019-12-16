package com.stoldo.fitness_app_android.model;

import androidx.annotation.LayoutRes;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.annotaions.FormField;
import com.stoldo.fitness_app_android.model.enums.FormFieldType;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;

import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Workout implements ListItem {
    private Integer id;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String title;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String title3;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String title4;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String title5;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String title6;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String title7;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String description;

    private List<Exercise> exercises;

    @Override
    public String getExtra() {
        return null;
    }
}