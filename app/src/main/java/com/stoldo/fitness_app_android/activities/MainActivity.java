package com.stoldo.fitness_app_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.PopupWindow;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.FormFragment;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.ListViewData;
import com.stoldo.fitness_app_android.model.Workout;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Subscriber {
    private List<Workout> workouts = new ArrayList<>();
    private JsonUtil<Workout> jsonUtil = new JsonUtil<>();
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activity_workouts);
        workouts = getWorkouts();

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
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.workout_list_container, ListViewFragment.newInstance(listViewData))
                    .commitNow();
        }
    }

    @Override
    public void update(Map<String, Object> data) {
//        Log.d("MYDEBUG", "hello " + data.get("action") + ", " + data.get("editMode"));
    }

    private List<Workout> getWorkouts() {
        // TODO connect to service
        List<Workout> workouts = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Workout w = new Workout();

            w.setTitle("Workout Title " + i);
            w.setDescription("WK Description " + i);
            workouts.add(w);
        }

        return workouts;
    }

    public void defaultOnWorkoutClick(Workout clickedWorkout) {
        try {
            Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);
            intent.putExtra("WORKOUT_ID", clickedWorkout.getId());
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editOnWorkoutClick(Workout clickedWorkout) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.workout_list_container, FormFragment.newInstance(clickedWorkout)) // TODO workout as fragment param --> can we do a gneric edit
                // fragment? would be awsome, rember to block any click stuff from the listview!
                .commitNow();
    }
}
