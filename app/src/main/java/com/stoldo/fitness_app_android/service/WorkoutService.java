package com.stoldo.fitness_app_android.service;


import com.stoldo.fitness_app_android.model.abstracts.AbstractSyncService;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.data.entity.ExerciseEntity;
import com.stoldo.fitness_app_android.model.data.entity.WorkoutEntity;
import com.stoldo.fitness_app_android.repository.WorkoutRepository;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import java.sql.SQLException;
import java.util.List;

@Singleton
public class WorkoutService extends AbstractSyncService {
    private WorkoutRepository workoutRepository = (WorkoutRepository) OtherUtil.getSingleton(WorkoutRepository.class);
    private ExerciseService exerciseService = (ExerciseService) OtherUtil.getSingleton(ExerciseService.class);

    private WorkoutService() {}

    public List<WorkoutEntity> getWorkouts() throws SQLException {
        List<WorkoutEntity> workouts = workoutRepository.findAll();

        for (WorkoutEntity workout : workouts) {
            workout.setExercises(exerciseService.getExercisesByWorkoutId(workout.getId()));
        }

        return workouts;
    }

    public WorkoutEntity getWorkoutById(Integer id) throws SQLException {
        WorkoutEntity workout = workoutRepository.getById(id);
        workout.setExercises(exerciseService.getExercisesByWorkoutId(workout.getId()));
        return workout;
    }

    public WorkoutEntity saveWorkout(WorkoutEntity workout) throws Exception {
        for (ExerciseEntity exercise : workout.getExercises()) {
            exerciseService.saveExercise(exercise);
        }

        return workoutRepository.save(workout);
    }
}
