package com.stoldo.fitness_app_android.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.Exercise;
import com.stoldo.fitness_app_android.model.Workout;
import com.stoldo.fitness_app_android.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;


public class ExerciseListActivity extends AppCompatActivity {
    private List<Exercise> exercises = new ArrayList<>();
    private JsonUtil<Workout> workoutJsonUtil = new JsonUtil<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        setTitle(R.string.activity_exercise_list);

        Integer workoutId = getIntent().getIntExtra("WORKOUT_ID", 0);
        exercises = getExercisesByWorkoutId(workoutId);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.exercise_list_container, ListViewFragment.<Exercise>newInstance(exercises, getExerciseClickListener()))
//                    .commitNow();
//        }
    }

    private List<Exercise> getExercisesByWorkoutId(Integer workoutId) {
        // TODO connect to service
        List<Exercise> workouts = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Exercise e = new Exercise();

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

    public AdapterView.OnItemClickListener getExerciseClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise clickedWorkout = exercises.get(position);

                Log.d("MYDEBUG", "Cliekd on exer");

                // TODO on excersie click open info which includes the play button --> Maybe refctor excerises to have warmup and rest

//     fragment from click listener
//                ItemInformationFragment frag = ItemInformationFragment.newInstance("New Frag " + position);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.element_list_container, frag, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
            }
        };
    }
}
