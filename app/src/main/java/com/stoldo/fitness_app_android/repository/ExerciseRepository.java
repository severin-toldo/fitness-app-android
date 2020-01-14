package com.stoldo.fitness_app_android.repository;

import android.content.Context;

import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRepository;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.data.entity.ExerciseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ExerciseRepository extends AbstractBaseRepository<ExerciseEntity> {
    private ExerciseRepository(Context context) throws SQLException {
        super(context, ExerciseEntity.class);
    }

    public List<ExerciseEntity> getExercisesByWorkoutId(Integer workoutId) throws SQLException {
        // TODO remove
        List<ExerciseEntity> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ExerciseEntity exerciseEntity = new ExerciseEntity(1, "Exercise " + i, i + 3);
            exerciseEntity.setId(i + 10);
            list.add(exerciseEntity);
        }

        list.addAll(getEntityDao().queryForEq("workout_id", workoutId));

        return list;
    }
}
