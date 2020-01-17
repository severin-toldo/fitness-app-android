package com.stoldo.fitness_app_android.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.FormFragment;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.data.ListViewData;
import com.stoldo.fitness_app_android.model.data.entity.WorkoutEntity;
import com.stoldo.fitness_app_android.model.data.events.ActionEvent;
import com.stoldo.fitness_app_android.model.enums.ErrorCode;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.model.interfaces.Submitable;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.service.SingletonService;
import com.stoldo.fitness_app_android.service.WorkoutService;
import com.stoldo.fitness_app_android.util.LogUtil;
import com.stoldo.fitness_app_android.util.OtherUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Subscriber<ActionEvent>, Submitable {
    private List<WorkoutEntity> workouts = new ArrayList<>();
    private ListViewFragment workoutListViewFragment = null;
    private WorkoutService workoutService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            setContentView(R.layout.activity_main);
            setTitle(R.string.activity_workouts);

            // setup singletons once
            SingletonService singletonService = SingletonService.getInstance(this);
            singletonService.instantiateSingletons();

            workoutService = (WorkoutService) singletonService.getSingletonByClass(WorkoutService.class);
            workouts = workoutService.getWorkouts();
            setUpWorkoutListView(savedInstanceState);
        } catch (SQLException sqle) {
            LogUtil.logError(sqle.getMessage(), this.getClass(), sqle);
        } catch (Exception e) {
            LogUtil.logErrorAndExit(e.getMessage(), this.getClass(), e);
        }
    }

    @Override
    public void update(ActionEvent data) {
        if (data != null) {
            switch (data.getActionType()) {
                case ADD:
                    setUpEditForm();
                    break;
                case CONFIRM:
                    saveWorkouts();
                    break;
            }
        }
    }

    private void saveWorkouts() {
        try {
            workoutService.saveWorkouts(workoutListViewFragment.getBaseItems());
        } catch (SQLException e) {
            LogUtil.logError(ErrorCode.E1008.getErrorMsg(), getClass(), e);
            OtherUtil.popToast(this, ErrorCode.E1008.getErrorMsg());
        }
    }

    @Override
    public Object onSubmit(Object value) {
        WorkoutEntity newWorkout = (WorkoutEntity)value;
        if (newWorkout != null && !this.workouts.contains(newWorkout)){
            this.workouts.add(newWorkout);
        }
        workoutListViewFragment.updateItems(this.workouts);
        return null;
    }

    public void defaultOnWorkoutClick(WorkoutEntity clickedWorkout) {
        Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);
        intent.putExtra(IntentParams.WORKOUT_ID.name(), clickedWorkout.getId());
        startActivity(intent);
    }

    public void editOnWorkoutClick(WorkoutEntity clickedWorkoutEntity) {
        FormFragment formFragment = FormFragment.newInstance(clickedWorkoutEntity);
        formFragment.setSubmitable(this::onSubmit);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.workout_list_container, formFragment)
                .commitNow();
    }

    // TODO make generic if possible
    private void setUpWorkoutListView(Bundle savedInstanceState) throws Exception {
        ListViewData<MainActivity, WorkoutEntity> listViewData = new ListViewData<>();
        listViewData.setItems(workouts);
        listViewData.setItemLayout(R.layout.workout_item);
        listViewData.setListViewSubscriber(this);
        listViewData.setDefaultItemClickMethod(MainActivity.class.getDeclaredMethod("defaultOnWorkoutClick", WorkoutEntity.class));
        listViewData.setEditItemClickMethod(MainActivity.class.getDeclaredMethod("editOnWorkoutClick", WorkoutEntity.class));

        workoutListViewFragment = ListViewFragment.newInstance(listViewData);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.workout_list_container, workoutListViewFragment)
                    .commitNow();
        }
    }

    // TODO make generic
    private void setUpEditForm() {
        FormFragment formFragment = FormFragment.newInstance(new WorkoutEntity());
        formFragment.setSubmitable(this::onSubmit);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.workout_list_container, formFragment)
                .commitNow();
    }
}
