package com.stoldo.fitness_app_android.model.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.interfaces.Entity;
import com.stoldo.fitness_app_android.model.annotaions.FormField;
import com.stoldo.fitness_app_android.model.enums.FormFieldType;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
public class WorkoutEntity implements ListItem, Entity {
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false, unique = true)
    private Integer id;

    @DatabaseField(columnName = "title", canBeNull = false)
    @FormField(type = FormFieldType.TEXTFIELD, labelResRef = R.string.title, index = 0)
    private String title;

    @DatabaseField(columnName = "description")
    @FormField(type = FormFieldType.TEXTFIELD, labelResRef = R.string.description, index = 0)
    private String description = "";

    private List<ExerciseEntity> exercises = new ArrayList<>();

    @Override
    public String getExtra() {
        return null;
    }

    /**
     * Not null constrcutor. Id is not null, but is not something you "know" when instantiating the object. The id is set when saving to db.
     * */
    public WorkoutEntity(String title) {
        this.title = title;
    }
}