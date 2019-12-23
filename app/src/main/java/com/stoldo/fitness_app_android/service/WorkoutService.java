package com.stoldo.fitness_app_android.service;

import com.stoldo.fitness_app_android.model.Workout;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton // TODO extend sync service
public class WorkoutService {
    private WorkoutService() {}

    // TODO implement real
    public List<Workout> getWorkouts() {
        List<Workout> workouts = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Workout w = new Workout();

            w.setTitle("Workout Title " + i);
            w.setDescription("WK Description " + i);
            workouts.add(w);
        }

        return workouts;
    }
}
