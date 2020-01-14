package com.stoldo.fitness_app_android.service;


import com.stoldo.fitness_app_android.model.abstracts.AbstractSyncService;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.data.entity.ExerciseEntity;
import com.stoldo.fitness_app_android.repository.ExerciseRepository;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import java.sql.SQLException;
import java.util.List;


@Singleton
public class ExerciseService extends AbstractSyncService {
    private ExerciseRepository exerciseRepository = (ExerciseRepository) OtherUtil.getSingletonInstance(ExerciseRepository.class);

    private ExerciseService() {}

    public List<ExerciseEntity> getExercisesByWorkoutId(Integer workoutId) throws SQLException {
        return exerciseRepository.getExercisesByWorkoutId(workoutId);
    }

    public List<ExerciseEntity> getAllExercises() throws SQLException {
        return exerciseRepository.findAll();
    }

    public ExerciseEntity saveExercise(ExerciseEntity exercise) throws SQLException {
        return exerciseRepository.save(exercise);
    }
}
