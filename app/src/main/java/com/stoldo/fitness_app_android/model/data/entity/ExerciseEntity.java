package com.stoldo.fitness_app_android.model.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.interfaces.Entity;
import com.stoldo.fitness_app_android.model.annotaions.FormField;
import com.stoldo.fitness_app_android.model.enums.FormFieldType;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;

import lombok.NoArgsConstructor;

// TODO set index correctly
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
    @FormField(type = FormFieldType.NUMBERFIELD, labelResRef = R.string.prepare_seconds, index = 0)
    private Integer prepareSeconds = 0;

    @DatabaseField(columnName = "title", canBeNull = false)
    @FormField(type = FormFieldType.TEXTFIELD, labelResRef = R.string.title, index = 0)
    private String title;

    @DatabaseField(columnName = "seconds", canBeNull = false)
    @FormField(type = FormFieldType.NUMBERFIELD, labelResRef = R.string.seconds, index = 0)
    private Integer seconds;

    @DatabaseField(columnName = "description")
    @FormField(type = FormFieldType.TEXTFIELD, labelResRef = R.string.description, index = 0)
    private String description = "";

    @DatabaseField(columnName = "note")
    @FormField(type = FormFieldType.TEXTAREA, labelResRef = R.string.note, index = 0)
    private String note = "";

    @DatabaseField(columnName = "position")
    @FormField(type = FormFieldType.TEXTFIELD, labelResRef = R.string.position, index = 0)
    private String position = "";

    @DatabaseField(columnName = "level")
    @FormField(type = FormFieldType.TEXTFIELD, labelResRef = R.string.level, index = 0)
    private String level = ""; // weight or niveu, ex. 20kg, Niveau 5

    @DatabaseField(columnName = "restSeconds")
    @FormField(type = FormFieldType.NUMBERFIELD, labelResRef = R.string.rest_seconds, index = 0)
    private Integer restSeconds = 0;

    // TODO implement properly
//    @FormField(type = FormFieldType.IMAGE)
//    private List<String> imagePaths; // TODO make sure to support also gif format

    @Override
    public String getExtra() {
        return seconds.toString();
    }

    /**
     * Not null constrcutor. Id is not null, but is not something you "know" when instantiating the object. The id is set when saving to db.
     *
     * @param workoutId a workout id is always needed to enusure the composition
     * */
    public ExerciseEntity(Integer workoutId, String title, Integer seconds) {
        this.workoutId = workoutId;
        this.title = title;
        this.seconds = seconds;
    }
}
