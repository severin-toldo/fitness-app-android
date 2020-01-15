package com.stoldo.fitness_app_android.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.FormFragment;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.data.entity.ExerciseEntity;
import com.stoldo.fitness_app_android.model.data.ListViewData;
import com.stoldo.fitness_app_android.model.data.entity.WorkoutEntity;
import com.stoldo.fitness_app_android.model.data.events.ActionEvent;
import com.stoldo.fitness_app_android.model.enums.ActionType;
import com.stoldo.fitness_app_android.model.enums.ErrorCode;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.model.interfaces.Submitable;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.service.ExerciseService;
import com.stoldo.fitness_app_android.shared.util.LogUtil;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ExerciseListActivity extends AppCompatActivity implements Subscriber<ActionEvent>, Submitable {
    private List<ExerciseEntity> exercises = new ArrayList<>();
    private Integer workoutId;
    private ExerciseService exerciseService = (ExerciseService) OtherUtil.getSingletonInstance(ExerciseService.class);
    private ListViewFragment exerciselistViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        setTitle(R.string.activity_exercise_list);

        try {
            Integer workoutId = getIntent().getIntExtra(IntentParams.WORKOUT_ID.name(), 0);
            this.workoutId = workoutId;
            exercises = exerciseService.getExercisesByWorkoutId(workoutId);
            setUpExerciseListView(savedInstanceState);
        } catch (SQLException sqle) {
            LogUtil.logError(sqle.getMessage(), this.getClass(), sqle);
        } catch (Exception e) {
            LogUtil.logErrorAndExit(e.getMessage(), this.getClass(), e);
        }
    }

    @Override
    public void update(ActionEvent data) {
        if(data != null) {
            if(data.getActionType() == ActionType.ADD){
                FormFragment formFragment = FormFragment.newInstance(new WorkoutEntity());
                formFragment.setSubmitable(this::onSubmit);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.exercise_list_container, formFragment)
                        .commitNow();
            }
        }
    }

    // TODO fix this, I cant get it to work. for me only the form opens but with wrong fields and save throws error --> Stefano
    @Override
    public Object onSubmit(Object value) {
        try {
            ExerciseEntity newExercise = (ExerciseEntity) value;

            if (newExercise != null && !this.exercises.contains(newExercise)){
                this.exercises.add(newExercise);
            }

            // first save, then update ui
            exerciseService.saveExercises(exercises);
            exerciselistViewFragment.updateItems(exercises);
        } catch (SQLException e) {
            LogUtil.logError(ErrorCode.E1008.getErrorMsg(), getClass(), e);
            OtherUtil.popToast(this, ErrorCode.E1008.getErrorMsg());
        }

        return null;
    }

    private void setUpExerciseListView(Bundle savedInstanceState) throws Exception {
        ListViewData<ExerciseListActivity, ExerciseEntity> listViewData = new ListViewData<>();
        listViewData.setItems(exercises);
        listViewData.setItemLayout(R.layout.exercise_item);
        listViewData.setListViewSubscriber(this);
        listViewData.setDefaultItemClickMethod(ExerciseListActivity.class.getDeclaredMethod("defaultOnExerciseClick", ExerciseEntity.class));
        listViewData.setEditItemClickMethod(ExerciseListActivity.class.getDeclaredMethod("editOnExerciseClick", ExerciseEntity.class));

        exerciselistViewFragment = ListViewFragment.newInstance(listViewData);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.exercise_list_container, exerciselistViewFragment)
                    .commitNow();
        }
    }

    public void defaultOnExerciseClick(ExerciseEntity clickedExercise) {
        Intent intent = new Intent(ExerciseListActivity.this, ExerciseStartActivity.class);
        intent.putExtra(IntentParams.EXERCISE_ID.name(), clickedExercise.getId());
        intent.putExtra(IntentParams.WORKOUT_ID.name(), workoutId);
        startActivity(intent);
    }

    public void editOnExerciseClick(ExerciseEntity clickedExercise) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.exercise_list_container, FormFragment.newInstance(clickedExercise))
                .commitNow();
    }
}
