package com.stoldo.fitness_app_android.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.Exercise;
import com.stoldo.fitness_app_android.model.ListViewData;
import com.stoldo.fitness_app_android.model.interfaces.Event;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.service.ExerciseService;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import java.util.ArrayList;
import java.util.List;


public class ExerciseListActivity extends AppCompatActivity implements Subscriber {
    private List<Exercise> exercises = new ArrayList<>();
    private Integer workoutId;
    private ExerciseService exerciseService = (ExerciseService) OtherUtil.getService(ExerciseService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        setTitle(R.string.activity_exercise_list);

        // TODO proper error handling
        try {
            Integer workoutId = getIntent().getIntExtra(IntentParams.WORKOUT_ID.name(), 0);
            this.workoutId = workoutId;
            exercises = exerciseService.getExercisesByWorkoutId(workoutId);

            setUpExerciseListView(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Event event) {
        // TODO
    }

    private void setUpExerciseListView(Bundle savedInstanceState) throws Exception {
        ListViewData<ExerciseListActivity, Exercise> listViewData = new ListViewData<>();
        listViewData.setItems(exercises);
        listViewData.setItemLayout(R.layout.exercise_item);
        listViewData.setListViewSubscriber(this);
        listViewData.setDefaultItemClickMethod(ExerciseListActivity.class.getDeclaredMethod("defaultOnExerciseClick", Exercise.class));
        listViewData.setEditItemClickMethod(ExerciseListActivity.class.getDeclaredMethod("editOnExerciseClick", Exercise.class));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.exercise_list_container, ListViewFragment.newInstance(listViewData))
                    .commitNow();
        }
    }

    public void defaultOnExerciseClick(Exercise clickedExercise) {
        // TODO error handling
        try {
            Intent intent = new Intent(ExerciseListActivity.this, ExerciseStartActivity.class);
            intent.putExtra(IntentParams.EXERCISE_ID.name(), clickedExercise.getId());
            intent.putExtra(IntentParams.WORKOUT_ID.name(), workoutId);
            startActivity(intent);
        } catch (Exception e) {
            // TODO notify parent or fragment
            e.printStackTrace();
        }
    }

    // TODO
    // Hint: Edit click listener should start FormFragment do edit an exercise
    public void editOnExerciseClick(Exercise clickedExercise) {
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.workout_list_container, FormFragment.newInstance(clickedWorkout, this,this))
//                .commitNow();
    }
}
