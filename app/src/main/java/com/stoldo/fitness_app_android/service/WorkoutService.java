package com.stoldo.fitness_app_android.service;

import com.stoldo.fitness_app_android.model.Exercise;
import com.stoldo.fitness_app_android.model.Workout;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton // TODO extend sync service
public class WorkoutService {
    private WorkoutService() {}

    // TODO implement real
    // TODO javadoc: get all workouts
    public List<Workout> getWorkouts() {
        List<Workout> workouts = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Workout w = new Workout();

            w.setId(i);
            w.setTitle("Workout Title " + i);
            w.setDescription("WK Description " + i);
            workouts.add(w);
        }

        return workouts;
    }

    // TODO implement real
    public Workout getWorkoutById(Integer id) {
        Workout w = new Workout();

        w.setId(1);
        w.setDescription("Demo Workout");
        w.setTitle("Demo WO Title");

        List<Exercise> exercises = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Exercise e = new Exercise();
            e.setId(i);
            e.setPosition("3 / 4");
            e.setNote("Im Note " + i);
            e.setLevel("34kg");
            e.setSeconds(10 + i);
            e.setDescription("Exercise description " + i);
            e.setTitle("Title E " + i);
            e.setRestSeconds(5 + i);;
            e.setImagePaths(Arrays.asList("path/to/image/" + i));
            e.setPrepareSeconds(i);

            exercises.add(e);
        }

        w.setExercises(exercises);

        return w;
    }
}
