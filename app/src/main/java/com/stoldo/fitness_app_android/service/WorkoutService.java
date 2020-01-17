package com.stoldo.fitness_app_android.service;


import com.stoldo.fitness_app_android.model.abstracts.AbstractSyncService;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.data.entity.WorkoutEntity;
import com.stoldo.fitness_app_android.repository.WorkoutRepository;
import com.stoldo.fitness_app_android.util.OtherUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class WorkoutService extends AbstractSyncService {
    private WorkoutRepository workoutRepository = (WorkoutRepository) OtherUtil.getSingletonInstance(WorkoutRepository.class);
    private ExerciseService exerciseService = (ExerciseService) OtherUtil.getSingletonInstance(ExerciseService.class);

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

    public WorkoutEntity saveWorkout(WorkoutEntity workout) throws SQLException {
        exerciseService.saveExercises(workout.getExercises());
        return workoutRepository.save(workout);
    }

    public List<WorkoutEntity> saveWorkouts(List<WorkoutEntity> workouts) throws SQLException {
        workoutRepository.flushTable(); // without this, deleted items stay in the db
        List<WorkoutEntity> savedWorkouts = new ArrayList<>();

        for (WorkoutEntity workout : workouts) {
            savedWorkouts.add(saveWorkout(workout));
        }

        return savedWorkouts;
    }
}
