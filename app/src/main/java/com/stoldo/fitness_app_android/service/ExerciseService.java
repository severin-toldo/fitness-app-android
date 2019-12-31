package com.stoldo.fitness_app_android.service;

import com.stoldo.fitness_app_android.model.Exercise;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton // TODO extend sync service
public class ExerciseService {
    private ExerciseService() {}

    // TODO implement real
    public List<Exercise> getExercisesByWorkoutId(Integer workoutId) {
        List<Exercise> workouts = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Exercise e = new Exercise();

            e.setId(i);
            e.setTitle("Exercise title " + i);
            e.setDescription("Desc " + i);
            e.setSeconds(i + 10);
            e.setLevel("10");
            e.setNote("Note " + i);
            e.setPosition("Pos " + i);

            workouts.add(e);
        }

        return workouts;
    }
}
