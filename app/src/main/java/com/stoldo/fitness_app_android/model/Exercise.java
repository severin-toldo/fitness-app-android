package com.stoldo.fitness_app_android.model;

import com.stoldo.fitness_app_android.model.annotaions.FormField;
import com.stoldo.fitness_app_android.model.enums.FormFieldType;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;

import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Exercise implements ListItem {
    private Integer id;

    @FormField(type = FormFieldType.NUMBERFIELD)
    private Integer prepareSeconds;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String title;

    @FormField(type = FormFieldType.NUMBERFIELD)
    private Integer seconds;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String description;

    @FormField(type = FormFieldType.TEXTAREA)
    private String note;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String position;

    @FormField(type = FormFieldType.TEXTFIELD)
    private String level; // weight or niveu, ex. 20kg, Niveau 5

    @FormField(type = FormFieldType.NUMBERFIELD)
    private Integer restSeconds;

    @FormField(type = FormFieldType.IMAGE)
    private List<String> imagePaths; // TODO make sure to support also gif format

    @Override
    public String getExtra() {
        return seconds.toString();
    }
}
