package com.stoldo.fitness_app_android.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.Exercise;
import com.stoldo.fitness_app_android.service.ExerciseService;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import java.util.ArrayList;
import java.util.List;


public class ExerciseListActivity extends AppCompatActivity {
    private List<Exercise> exercises = new ArrayList<>();
    private Integer workoutId;
    private ExerciseService exerciseService = (ExerciseService) OtherUtil.getService(ExerciseService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        setTitle(R.string.activity_exercise_list);

        Integer workoutId = getIntent().getIntExtra(IntentParams.WORKOUT_ID.name(), 0);
        exercises = exerciseService.getExercisesByWorkoutId(workoutId);
        this.workoutId = workoutId;

        // TODO use ListViewData here. Hint: Normal click listneer should start ExerciseStartAvticy, Edit click listener should start FormFragment
        // TODO see mainactivity for examples
        // TODO layout: R.layout.exercise_item
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.exercise_list_container, ListViewFragment.<Exercise>newInstance(exercises, getExerciseClickListener()))
//                    .commitNow();
//        }
    }

    // TODO refavtor to normal method instead of actual click listener --> stuff is new over reflection!
//    public AdapterView.OnItemClickListener getExerciseClickListener() {
//        return (AdapterView<?> parent, View view, int position, long id) -> {
//            Exercise clickedExercise = exercises.get(position);
//
//            Intent intent = new Intent(ExerciseListActivity.this, ExerciseStartActivity.class);
//            intent.putExtra(IntentParams.EXERCISE_ID.name(), clickedExercise.getId());
//            intent.putExtra(IntentParams.WORKOUT_ID.name(), workoutId);
//            startActivity(intent);
//        };
//    }
}
