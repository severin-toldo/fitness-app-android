package com.stoldo.fitness_app_android.model;

import java.util.List;

public class Workout {
    private String title;
    private String desctription;
    private List<Exercise> exercises;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


