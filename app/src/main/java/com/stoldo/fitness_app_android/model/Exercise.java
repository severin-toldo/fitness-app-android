package com.stoldo.fitness_app_android.model;

import java.util.List;

public class Exercise {
    private String title;
    private String description;
    private String note;
    private String position;
    private String level; // weight or niveu, ex. 20kg, Niveau 5
    private List<String> imagePaths;






    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getImagePath() {
        return imagePaths;
    }

    public void setImagePath(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
}
