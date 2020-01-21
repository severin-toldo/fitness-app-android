package com.stoldo.fitness_app_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.data.entity.WorkoutEntity;
import com.stoldo.fitness_app_android.model.enums.ErrorCode;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.service.SingletonService;
import com.stoldo.fitness_app_android.service.WorkoutService;
import com.stoldo.fitness_app_android.util.LogUtil;
import com.stoldo.fitness_app_android.util.OtherUtil;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends BaseListViewActivity<MainActivity, WorkoutEntity, WorkoutService> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activity_workouts);

        try {
            //only once
            SingletonService singletonService = SingletonService.getInstance(this);
            singletonService.instantiateSingletons();

            this.initializeBaseAttributes(WorkoutEntity.class, WorkoutService.class, R.layout.workout_item, R.id.workout_list_container);

            List<WorkoutEntity> workouts = service.getWorkouts();
            setUpListView(savedInstanceState, workouts);
        } catch (SQLException sqle) {
            LogUtil.logError(sqle.getMessage(), this.getClass(), sqle);
        } catch (Exception e) {
            LogUtil.logErrorAndExit(e.getMessage(), this.getClass(), e);
        }
    }

    @Override
    protected void saveEntities() {
        try {
            service.saveWorkouts(listViewFragment.getBaseItems());
        } catch (SQLException e) {
            LogUtil.logError(ErrorCode.E1008.getErrorMsg(), getClass(), e);
            OtherUtil.popToast(this, ErrorCode.E1008.getErrorMsg());
        }
    }

    @Override
    public void defaultOnListItemClick(WorkoutEntity clickedWorkout) {
        Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);
        intent.putExtra(IntentParams.WORKOUT_ID.name(), clickedWorkout.getId());
        startActivity(intent);
    }

    @Override
    public void editOnListItemClick(WorkoutEntity clickedWorkoutEntity) {
        setUpEditForm(clickedWorkoutEntity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
