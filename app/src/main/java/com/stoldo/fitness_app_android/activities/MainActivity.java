package com.stoldo.fitness_app_android.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.FormFragment;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.ListViewData;
import com.stoldo.fitness_app_android.model.Workout;
import com.stoldo.fitness_app_android.model.interfaces.Submitable;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.service.SingletonService;
import com.stoldo.fitness_app_android.service.WorkoutService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Subscriber, Submitable {
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

    private void setUpWorkoutListView(Bundle savedInstanceState) {
        ListViewData<MainActivity, Workout> listViewData = new ListViewData<>();
        listViewData.setItems(workouts);
        listViewData.setItemLayout(R.layout.workout_item);
        listViewData.setListViewSubscriber(this);

        try {
            listViewData.setDefaultItemClickMethod(MainActivity.class.getDeclaredMethod("defaultOnWorkoutClick", Workout.class));
            listViewData.setEditItemClickMethod(MainActivity.class.getDeclaredMethod("editOnWorkoutClick", Workout.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (savedInstanceState == null) {
            workoutListViewFragment = ListViewFragment.newInstance(listViewData);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.workout_list_container, workoutListViewFragment)
                    .commitNow();
        }

    }

    private void updateWorkoutListView(){
        workoutListViewFragment.updateItems(this.workouts);
    }

    // TODO what is this used for? -> probably for FromFragment?
    @Override
    public void update(Map<String, Object> data) {
//        Log.d("MYDEBUG", "hello " + data.get("action") + ", " + data.get("editMode"));
    }

    public void defaultOnWorkoutClick(Workout clickedWorkout) {
        try {
            Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);
            intent.putExtra("WORKOUT_ID", clickedWorkout.getId());
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

    @Override
    public Object onSubmit(Object value) {
        updateWorkoutListView();
        return null;
    }
}
