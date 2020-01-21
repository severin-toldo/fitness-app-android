package com.stoldo.fitness_app_android.activities;

import android.content.Intent;
import android.os.Bundle;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.data.entity.ExerciseEntity;
import com.stoldo.fitness_app_android.model.enums.ErrorCode;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.service.ExerciseService;
import com.stoldo.fitness_app_android.util.LogUtil;
import com.stoldo.fitness_app_android.util.OtherUtil;

import java.sql.SQLException;
import java.util.List;


public class ExerciseListActivity extends BaseListViewActivity<ExerciseListActivity, ExerciseEntity, ExerciseService> {
    private Integer workoutId;

    @Override
    public boolean navigateUpTo(Intent upIntent) {
        return super.navigateUpTo(upIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        setTitle(R.string.activity_exercise_list);

        try {
            this.initializeBaseAttributes(ExerciseEntity.class, ExerciseService.class, R.layout.exercise_item, R.id.exercise_list_container);

            Integer workoutId = getIntent().getIntExtra(IntentParams.WORKOUT_ID.name(), 0);
            this.workoutId = workoutId;
            List<ExerciseEntity> exercises = service.getExercisesByWorkoutId(workoutId);
            setUpListView(savedInstanceState, exercises);
        } catch (SQLException sqle) {
            LogUtil.logError(sqle.getMessage(), this.getClass(), sqle);
        } catch (Exception e) {
            LogUtil.logErrorAndExit(e.getMessage(), this.getClass(), e);
        }
    }

    @Override
    protected void saveEntities() {
        try {
            List<ExerciseEntity> exercisesToSave = listViewFragment.getBaseItems();
            exercisesToSave.forEach(e -> e.setWorkoutId(workoutId));
            service.saveExercises(exercisesToSave);
        } catch (SQLException e) {
            LogUtil.logError(ErrorCode.E1008.getErrorMsg(), getClass(), e);
            OtherUtil.popToast(this, ErrorCode.E1008.getErrorMsg());
        }
    }

    @Override
    public void defaultOnListItemClick(ExerciseEntity clickedExercise) {
        Intent intent = new Intent(ExerciseListActivity.this, ExerciseStartActivity.class);
        intent.putExtra(IntentParams.EXERCISE_ID.name(), clickedExercise.getId());
        intent.putExtra(IntentParams.WORKOUT_ID.name(), workoutId);
        startActivity(intent);
    }
}
