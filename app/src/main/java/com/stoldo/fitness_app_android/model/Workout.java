package com.stoldo.fitness_app_android.model;

import androidx.annotation.LayoutRes;

import com.stoldo.fitness_app_android.R;

import java.util.List;

public class Workout implements ListItem {
    private String title;
    private String description;
    private List<Exercise> exercises;


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getExtra() {
        return null;
    }

    @Override
    public @LayoutRes int getItemLayout() {
        return R.layout.workout_item;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String desctription) {
        this.description = desctription;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}