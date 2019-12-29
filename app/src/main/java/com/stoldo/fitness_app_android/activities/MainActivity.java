package com.stoldo.fitness_app_android.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.FormFragment;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.ListViewData;
import com.stoldo.fitness_app_android.model.Workout;
import com.stoldo.fitness_app_android.model.data.events.ActionEvent;
import com.stoldo.fitness_app_android.model.interfaces.Submitable;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.service.SingletonService;
import com.stoldo.fitness_app_android.service.WorkoutService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Subscriber<ActionEvent>, Submitable {
    private List<Workout> workouts = new ArrayList<>();
    ListViewFragment workoutListViewFragment = null;
    private WorkoutService workoutService = null;

    // TODO proper error handling
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activity_workouts);

        try {
            // setup singletons once
            SingletonService singletonService = SingletonService.getInstance(this);
            singletonService.instantiateSingletons();

            workoutService = (WorkoutService) singletonService.getSingletonByClass(WorkoutService.class);
            workouts = workoutService.getWorkouts();

            setUpWorkoutListView(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ActionEvent data) {
        // TODO what is this used for? -> probably for FromFragment?
    }

    @Override
    public Object onSubmit(Object value) {
        workoutListViewFragment.updateItems(this.workouts);
        return null;
    }

    public void defaultOnWorkoutClick(Workout clickedWorkout) {
        try {
            Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);
            intent.putExtra(IntentParams.WORKOUT_ID.name(), clickedWorkout.getId());
            startActivity(intent);
        } catch (Exception e) {
            // TODO notify parent or fragment
            e.printStackTrace();
        }
    }

    public void editOnWorkoutClick(Workout clickedWorkout) {
        FormFragment formFragment = FormFragment.newInstance(clickedWorkout);
        formFragment.setSubmitable(this::onSubmit);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.workout_list_container, formFragment)
                .commitNow();
    }

    private void setUpWorkoutListView(Bundle savedInstanceState) throws Exception {
        ListViewData<MainActivity, Workout> listViewData = new ListViewData<>();
        listViewData.setItems(workouts);
        listViewData.setItemLayout(R.layout.workout_item);
        listViewData.setListViewSubscriber(this);
        listViewData.setDefaultItemClickMethod(MainActivity.class.getDeclaredMethod("defaultOnWorkoutClick", Workout.class));
        listViewData.setEditItemClickMethod(MainActivity.class.getDeclaredMethod("editOnWorkoutClick", Workout.class));

        workoutListViewFragment = ListViewFragment.newInstance(listViewData);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.workout_list_container, workoutListViewFragment)
                    .commitNow();
        }
    }
}
