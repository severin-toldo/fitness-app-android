package com.stoldo.fitness_app_android.activities;

import android.content.Intent;
import android.os.Bundle;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.FormFragment;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.data.ListViewData;
import com.stoldo.fitness_app_android.model.data.entity.ExerciseEntity;
import com.stoldo.fitness_app_android.model.data.events.ActionEvent;
import com.stoldo.fitness_app_android.model.enums.ErrorCode;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.model.interfaces.Submitable;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.service.ExerciseService;
import com.stoldo.fitness_app_android.util.LogUtil;
import com.stoldo.fitness_app_android.util.OtherUtil;

import java.sql.SQLException;
import java.util.List;


public class ExerciseListActivity extends BaseActivity implements Subscriber<ActionEvent>, Submitable {
    private Integer workoutId;
    private ListViewFragment exerciseListViewFragment = null;

    @Override
    public boolean navigateUpTo(Intent upIntent) {
        return super.navigateUpTo(upIntent);
    }

    private ExerciseService exerciseService = (ExerciseService) OtherUtil.getSingletonInstance(ExerciseService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        setTitle(R.string.activity_exercise_list);

        try {
            Integer workoutId = getIntent().getIntExtra(IntentParams.WORKOUT_ID.name(), 0);
            this.workoutId = workoutId;
            List<ExerciseEntity> exercises = exerciseService.getExercisesByWorkoutId(workoutId);
            setUpExerciseListView(savedInstanceState, exercises);
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
                    saveExercises();
                    break;
            }
        }
    }

    private void saveExercises() {
        try {
            List<ExerciseEntity> exercisesToSave = exerciseListViewFragment.getBaseItems();
            exercisesToSave.forEach(e -> e.setWorkoutId(workoutId));
            exerciseService.saveExercises(exercisesToSave);
        } catch (SQLException e) {
            LogUtil.logError(ErrorCode.E1008.getErrorMsg(), getClass(), e);
            OtherUtil.popToast(this, ErrorCode.E1008.getErrorMsg());
        }
    }

    // TODO make generic if possible
    private void setUpExerciseListView(Bundle savedInstanceState, List<ExerciseEntity> exercises) throws Exception {
        ListViewData<ExerciseListActivity, ExerciseEntity> listViewData = new ListViewData<>();
        listViewData.setItems(exercises);
        listViewData.setItemLayout(R.layout.exercise_item);
        listViewData.setListViewSubscriber(this);
        listViewData.setDefaultItemClickMethod(ExerciseListActivity.class.getDeclaredMethod("defaultOnExerciseClick", ExerciseEntity.class));
        listViewData.setEditItemClickMethod(ExerciseListActivity.class.getDeclaredMethod("editOnExerciseClick", ExerciseEntity.class));

        exerciseListViewFragment = ListViewFragment.newInstance(listViewData);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.exercise_list_container, exerciseListViewFragment)
                    .commitNow();
        }
    }

    // TODO make generic
    private void setUpEditForm() {
        FormFragment formFragment = FormFragment.newInstance(new ExerciseEntity());
        formFragment.setSubmitable(this::onSubmit);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.exercise_list_container, formFragment)
                .commitNow();
    }

    public void defaultOnExerciseClick(ExerciseEntity clickedExercise) {
        Intent intent = new Intent(ExerciseListActivity.this, ExerciseStartActivity.class);
        intent.putExtra(IntentParams.EXERCISE_ID.name(), clickedExercise.getId());
        intent.putExtra(IntentParams.WORKOUT_ID.name(), workoutId);
        startActivity(intent);
    }

    // Hint: Edit click listener should start FormFragment do edit an exercise
    public void editOnExerciseClick(ExerciseEntity clickedExercise) {
        FormFragment formFragment = FormFragment.newInstance(clickedExercise);
        formFragment.setSubmitable(this::onSubmit);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.exercise_list_container, formFragment)
                .commitNow();
    }

    @Override
    public Object onSubmit(Object value) {
            ExerciseEntity newExercise = (ExerciseEntity)value;
            List<ExerciseEntity> exercises = exerciseListViewFragment.getItems();
            if (newExercise != null && !exercises.contains(newExercise)){
                exercises.add(newExercise);
           }
        exerciseListViewFragment.updateItems(exercises);
        return null;
    }
}
