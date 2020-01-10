package com.stoldo.fitness_app_android.model.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.stoldo.fitness_app_android.model.interfaces.Entity;
import com.stoldo.fitness_app_android.model.annotaions.FormField;
import com.stoldo.fitness_app_android.model.enums.FormFieldType;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;

import lombok.NoArgsConstructor;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@DatabaseTable(tableName = "exercise")
public class ExerciseEntity implements ListItem, Entity {
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false, unique = true)
    protected Integer id;

    @DatabaseField(columnName = "workout_id", canBeNull = false)
    protected Integer workoutId;

    @DatabaseField(columnName = "prepare_seconds")
    @FormField(type = FormFieldType.NUMBERFIELD)
    private Integer prepareSeconds;

    @DatabaseField(columnName = "title", canBeNull = false)
    @FormField(type = FormFieldType.TEXTFIELD)
    private String title;

    @DatabaseField(columnName = "seconds", canBeNull = false)
    @FormField(type = FormFieldType.NUMBERFIELD)
    private Integer seconds;

    @DatabaseField(columnName = "description")
    @FormField(type = FormFieldType.TEXTFIELD)
    private String description;

    @DatabaseField(columnName = "note")
    @FormField(type = FormFieldType.TEXTAREA)
    private String note;

    @DatabaseField(columnName = "position")
    @FormField(type = FormFieldType.TEXTFIELD)
    private String position;

    @DatabaseField(columnName = "level")
    @FormField(type = FormFieldType.TEXTFIELD)
    private String level; // weight or niveu, ex. 20kg, Niveau 5

    @DatabaseField(columnName = "restSeconds")
    @FormField(type = FormFieldType.NUMBERFIELD)
    private Integer restSeconds;

    // TODO implement properly
//    @FormField(type = FormFieldType.IMAGE)
//    private List<String> imagePaths; // TODO make sure to support also gif format

    @Override
    public String getExtra() {
        return seconds.toString();
    }

    /**
     * Not null constrcutor. Id is not null, but is not something you "know" when instantiating the object. The id is set when saving to db.
     * */
    public ExerciseEntity(Integer workoutId, String title, Integer seconds) {
        this.workoutId = workoutId;
        this.title = title;
        this.seconds = seconds;
    }
}
