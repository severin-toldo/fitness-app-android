package com.stoldo.fitness_app_android.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.data.entity.ExerciseEntity;
import com.stoldo.fitness_app_android.model.data.events.SoundEvent;
import com.stoldo.fitness_app_android.model.data.events.TimerEvent;
import com.stoldo.fitness_app_android.model.enums.ActionType;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.model.enums.TimeType;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.service.SoundService;
import com.stoldo.fitness_app_android.service.TimerService;
import com.stoldo.fitness_app_android.service.WorkoutService;
import com.stoldo.fitness_app_android.util.LogUtil;
import com.stoldo.fitness_app_android.util.OtherUtil;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class ExerciseStartActivity extends BaseActivity implements Subscriber<TimerEvent> {
    /**
     * UI Views
     * */
    private ImageButton pauseExerciseButton;
    private ImageButton playExerciseButton;
    private ImageButton previousExerciseButton;
    private ImageButton nextExerciseButton;
    private TextView remainingSecondsTextView;
    private TextView nextExerciseTextView;
    private TextView titleTextView;
    private ConstraintLayout background;
    private TextView descriptionTextView;
    private TextView noteTextView;
    private TextView positionTextView;
    private TextView levelTextView;

    private List<ExerciseEntity> exercisesOfWorkout;
    private ExerciseEntity currentExercise;
    private Integer currentExerciseIndex = -1;
    private ExerciseEntity previousExercise;
    private ExerciseEntity nextExerciseEntity;
    private final TimeType defaultTimeType = TimeType.PREPARE;

    private boolean playButtonTouched = false;
    private TimeType currentTimeType = defaultTimeType;

    private WorkoutService workoutService = (WorkoutService) OtherUtil.getSingletonInstance(WorkoutService.class);
    private TimerService timerService = (TimerService) OtherUtil.getSingletonInstance(TimerService.class);
    private SoundService soundService = (SoundService) OtherUtil.getSingletonInstance(SoundService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_exercise_start);
            Integer currentExerciseId = getIntent().getIntExtra(IntentParams.EXERCISE_ID.name(), 0);
            exercisesOfWorkout = workoutService.getWorkoutById(getIntent().getIntExtra(IntentParams.WORKOUT_ID.name(), 0)).getExercises();

            // TODO use streams if possible
            for (int i = 0; i < exercisesOfWorkout.size(); i++) {
                if (exercisesOfWorkout.get(i).getId() == currentExerciseId) {
                    currentExerciseIndex = i;
                }
            }

            setUpViews();
            onExerciseChange(null);

        } catch (SQLException sqle) {
            LogUtil.logError(sqle.getMessage(), this.getClass(), sqle);
        } catch (Exception e) {
            LogUtil.logErrorAndExit(e.getMessage(), this.getClass(), e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerService.stopService();
        soundService.stopService();
    }

    /**
     * is called when seconds change.
     *
     * @param event The Event the seconds change triggers. If the type changes, the exercises changes.
     * */
    @Override
    public void update(TimerEvent event) {
        runOnUiThread(() -> {
            if (event.getSeconds() == 0 &&  event.getTimeType() == TimeType.REST) {
                onExerciseChange(ActionType.NEXT);
            } else {
                onSecondsChange(event);
            }
        });
    }

    /**
     * gets called every time the seconds of the timer changes and the exercise doesnt change.
     * based on the time type of the event, it changes background color, emits a sound and updates the seconds in the ui
     *
     * @param event
     * */
    private void onSecondsChange(TimerEvent event) {
        if (event.getTimeType() != currentTimeType) {
            currentTimeType = event.getTimeType();
            soundService.startService(Arrays.asList(new SoundEvent(getApplicationContext(), R.raw.boxing_bell)));
        } else if (event.getSeconds() == 3 || event.getSeconds() == 2 || event.getSeconds() == 1) {
            soundService.startService(Arrays.asList(new SoundEvent(getApplicationContext(), R.raw.countdown)));
        }

        remainingSecondsTextView.setText(event.getSeconds().toString());
        changeBackgroundColorByTimeType(event.getTimeType());
    }

    /**
     * Sets up all the views (click handler, virility etc)
     * */
    private void setUpViews() {
        pauseExerciseButton = findViewById(R.id.pauseExerciseButton);
        pauseExerciseButton.setVisibility(View.GONE); // hide on start
        pauseExerciseButton.setOnClickListener((View v) -> {
            onPauseExercise();
        });

        playExerciseButton = findViewById(R.id.playExerciseButton);
        playExerciseButton.setOnClickListener((View v) -> {
            onPlayExercise();
        });

        previousExerciseButton = findViewById(R.id.previousExerciseButton);
        previousExerciseButton.setOnClickListener((View v) -> {
            onPreviousExercise();
        });

        nextExerciseButton = findViewById(R.id.nextExerciseButton);
        nextExerciseButton.setOnClickListener((View v) -> {
            onNextExercise();
        });

        remainingSecondsTextView = findViewById(R.id.remainingSecondsTextView);
        remainingSecondsTextView.setTextColor(getResources().getColor(R.color.white));

        nextExerciseTextView = findViewById(R.id.nextExerciseTextView);
        nextExerciseTextView.setTextColor(getResources().getColor(R.color.white));

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        noteTextView = findViewById(R.id.noteTextView);
        levelTextView = findViewById(R.id.levelTextView);
        positionTextView = findViewById(R.id.positionTextView);

        background = findViewById(R.id.activity_exercise_start_container);
    }

    /**
     * gets called when the exercise changes and once at the start. Updates all the dependet data and stops services etc.
     *
     * @param action the action which should be performed. Valid calues: next, previos, null
     * */
    private void onExerciseChange(ActionType action) {
        timerService.stopService();

        // determine new current index
        if (action != null) {
            switch (action) {
                case NEXT:
                    if (OtherUtil.isValidIndex(currentExerciseIndex + 1, exercisesOfWorkout.size())) {
                        currentExerciseIndex++;
                    }
                    break;
                case PREVIOUS:
                    if (OtherUtil.isValidIndex(currentExerciseIndex - 1, exercisesOfWorkout.size())) {
                        currentExerciseIndex--;
                    }
                    break;
            }
        }

        // update exercises
        currentExercise = getExerciseByIndex(currentExerciseIndex);
        previousExercise = getExerciseByIndex(currentExerciseIndex - 1);
        nextExerciseEntity = getExerciseByIndex(currentExerciseIndex + 1);

        // update ui
        remainingSecondsTextView.setText(hyphenIfNullorEmpty(currentExercise.getPrepareSeconds().toString()));
        nextExerciseTextView.setText(hyphenIfNullorEmpty(nextExerciseEntity.getTitle()));
        titleTextView.setText(hyphenIfNullorEmpty(currentExercise.getTitle()));
        descriptionTextView.setText(hyphenIfNullorEmpty(currentExercise.getDescription()));
        noteTextView.setText(hyphenIfNullorEmpty(currentExercise.getNote()));
        levelTextView.setText(hyphenIfNullorEmpty(currentExercise.getLevel()));
        positionTextView.setText(hyphenIfNullorEmpty(currentExercise.getPosition()));
        changeBackgroundColorByTimeType(defaultTimeType);

        if (action == ActionType.NEXT || action == ActionType.PREVIOUS) {
            startExerciseCountdown();
        }
    }

    /**
     * Replaces the value with - if it is null or empty
     *
     * @param value Value to be checked
     * @return value or -
     * */
    private String hyphenIfNullorEmpty(String value){
        if(value == null || value.isEmpty()){
            return "-";
        }

        return value;
    }

    /**
     * Starts the timer for the current exercise by starting the timer service.
     * */
    private void startExerciseCountdown() {
        TimerEvent prepareEvent = new TimerEvent(currentExercise.getPrepareSeconds(), TimeType.PREPARE, Arrays.asList(this));
        TimerEvent workEvent = new TimerEvent(currentExercise.getSeconds(), TimeType.WORK, Arrays.asList(this));
        TimerEvent restEvent = new TimerEvent(currentExercise.getRestSeconds(), TimeType.REST, Arrays.asList(this));
        timerService.startService(Arrays.asList(prepareEvent, workEvent, restEvent));
    }

    /**
     * Gets an exercise by the index if the index is valid, otherwise returns the current exercise.
     *
     * @param index index of the desired exercise
     * @return The exercises at the given index or the current exercise if the index is invalid
     * */
    private ExerciseEntity getExerciseByIndex(Integer index) {
        if (OtherUtil.isValidIndex(index, exercisesOfWorkout.size())) {
            return exercisesOfWorkout.get(index);
        }

        return exercisesOfWorkout.get(currentExerciseIndex);
    }

    /**
     * Each time type has a different background color.
     * This method changes the background and the status bar according to it.
     *
     * @param timeType given time type on which to change the color
     * */
    private void changeBackgroundColorByTimeType(TimeType timeType) {
        OtherUtil.changeStatusbarColor(this, timeType.getBackgroundColor());
        background.setBackgroundColor(ContextCompat.getColor(this, timeType.getBackgroundColor()));
    }

    private void onPauseExercise() {
        showPlayButton();
        timerService.pause();
    }

    private void onPlayExercise() {
        showPauseButton();

        // start service on first time click, after that always resume
        if (playButtonTouched) {
            timerService.resume();
        } else {
            startExerciseCountdown();
        }

        playButtonTouched = true;
    }

    private void onPreviousExercise() {
        onExerciseChange(ActionType.PREVIOUS);
        showPauseButton();
    }

    private void onNextExercise() {
        onExerciseChange(ActionType.NEXT);
        showPauseButton();
    }

    private void showPlayButton() {
        playExerciseButton.setVisibility(View.VISIBLE);
        pauseExerciseButton.setVisibility(View.GONE);
    }

    private void showPauseButton() {
        playExerciseButton.setVisibility(View.GONE);
        pauseExerciseButton.setVisibility(View.VISIBLE);
    }
}

