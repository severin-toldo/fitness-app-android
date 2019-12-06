package com.stoldo.fitness_app_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.Workout;
import com.stoldo.fitness_app_android.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Workout> workouts = new ArrayList<>();
    private JsonUtil<Workout> jsonUtil = new JsonUtil<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activity_workouts);
        workouts = getWorkouts();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ListViewFragment.<Workout>newInstance(workouts, getWorkoutClickListener()))
                    .commitNow();
        }
    }

    private List<Workout> getWorkouts() {
        // TODO connect to service
        List<Workout> workouts = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Workout w = new Workout();

            w.setTitle("Workout Title " + i);
            w.setDescription("WK Description " + i);
            workouts.add(w);
        }

        return workouts;
    }

    public AdapterView.OnItemClickListener getWorkoutClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Workout clickedWorkout = workouts.get(position);

                try {
                    Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);
                    intent.putExtra("WORKOUT_ID", clickedWorkout.getId());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
