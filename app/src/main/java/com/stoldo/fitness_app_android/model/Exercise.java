package com.stoldo.fitness_app_android.model;

import androidx.annotation.LayoutRes;

import com.stoldo.fitness_app_android.R;

import java.util.List;

public class Exercise implements ListItem {
    private String title;
    private Integer seconds;
    private String description;
    private String note;
    private String position;
    private String level; // weight or niveu, ex. 20kg, Niveau 5
    private List<String> imagePaths;


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
        return seconds.toString();
    }

    @Override
    public @LayoutRes int getItemLayout() {
        return R.layout.exercise_item;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
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
