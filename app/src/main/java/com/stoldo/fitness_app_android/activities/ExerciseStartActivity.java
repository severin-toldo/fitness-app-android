package com.stoldo.fitness_app_android.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.Exercise;
import com.stoldo.fitness_app_android.model.data.events.TimerEvent;
import com.stoldo.fitness_app_android.model.enums.IntentParams;
import com.stoldo.fitness_app_android.model.enums.TimeType;
import com.stoldo.fitness_app_android.model.interfaces.Event;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.service.TimerService;
import com.stoldo.fitness_app_android.service.WorkoutService;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import java.util.Arrays;
import java.util.List;


public class ExerciseStartActivity extends AppCompatActivity implements Subscriber {
    // Views
    private ImageButton pauseExerciseButton;
    private ImageButton playExerciseButton;
    private ImageButton previousExerciseButton;
    private ImageButton nextExerciseButton;
    private TextView remainingSecondsTextView;
    private TextView nextExerciseTextView;

    private List<Exercise> exercisesOfWorkout;
    private Exercise currentExercise;
    private Integer currentExerciseIndex = -1;
    private Exercise previousExercise;
    private Exercise nextExercise;
    private boolean playButtonTouched = false;

    private WorkoutService workoutService = (WorkoutService) OtherUtil.getService(WorkoutService.class);
    private TimerService timerService = (TimerService) OtherUtil.getService(TimerService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO error handling
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
            updateData(null);

        } catch (Exception e) {
            Log.d("MYDEBUG", e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerService.stopService();
    }

    /**
     * is called when seconds change.
     * */
    @Override
    public void update(Event event) {
        // TODO cast timer event first then also handle timetypes etc
        // TODO falseTheThrows
        Integer remainingSeconds = ((TimerEvent) event).getSeconds();

        runOnUiThread(() -> {
            if (remainingSeconds != 0) {
                remainingSecondsTextView.setText(remainingSeconds.toString());
            } else {
                // start next exercise
            }
        });
    }

    private void setUpViews() {
        pauseExerciseButton = findViewById(R.id.pauseExerciseButton);
        pauseExerciseButton.setVisibility(View.GONE); // hide on start
        pauseExerciseButton.setOnClickListener((View v) -> {
            // TODO error handling
            try {
                onPauseExercise();
            } catch (Exception e) {
                Log.d("MYDEBUG", "Error!" + e.getMessage());
            }
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
        nextExerciseTextView = findViewById(R.id.nextExerciseTextView);
    }

    // TODO unit test
    /**
     * this method is called on previous or next button clicked. Updates all the dependet data and stops services etc.
     * */
    private void updateData(String action) {
        // determine new current index
        if (action != null) {
            switch (action) {
                case "next":
                    if (OtherUtil.isValidIndex(currentExerciseIndex + 1, exercisesOfWorkout.size())) {
                        currentExerciseIndex++;
                    }
                    break;
                case "previous":
                    if (OtherUtil.isValidIndex(currentExerciseIndex - 1, exercisesOfWorkout.size())) {
                        currentExerciseIndex--;
                    }
                    break;
            }
        }

        // update exercises
        currentExercise = getExerciseByIndex(currentExerciseIndex);
        previousExercise = getExerciseByIndex(currentExerciseIndex - 1);
        nextExercise = getExerciseByIndex(currentExerciseIndex + 1);

        // update ui
        remainingSecondsTextView.setText(currentExercise.getPrepareSeconds().toString());
        nextExerciseTextView.setText(nextExercise.getTitle());

        // update data and stop timer service
        playButtonTouched = false;
        timerService.stopService();
    }

    private Exercise getExerciseByIndex(Integer index) {
        if (OtherUtil.isValidIndex(index, exercisesOfWorkout.size())) {
            return exercisesOfWorkout.get(index);
        }

        return exercisesOfWorkout.get(currentExerciseIndex);
    }

    private void onPauseExercise() throws Exception {
        playExerciseButton.setVisibility(View.VISIBLE);
        pauseExerciseButton.setVisibility(View.GONE);
        timerService.pause();
    }

    private void onPlayExercise() {
        playExerciseButton.setVisibility(View.GONE);
        pauseExerciseButton.setVisibility(View.VISIBLE);

        // TODO implement cleaner solution
        // start service on first time click, after that always resume
        if (playButtonTouched) {
            timerService.resume();
        } else {
            // TODO after that next step: implement prepare and break times. --> color change
            // TODO put in method -> update current exercise adn move avay from here.
            // TODO also pass break and prepare
            // TODO consider prepare and break... --> update with type and also color change

            TimerEvent prepareEvent = new TimerEvent(currentExercise.getPrepareSeconds(), TimeType.PREPARE, Arrays.asList(this));
            TimerEvent workEvent = new TimerEvent(currentExercise.getSeconds(), TimeType.WORK, Arrays.asList(this));
            TimerEvent restEvent = new TimerEvent(currentExercise.getRestSeconds(), TimeType.REST, Arrays.asList(this));

            timerService.startService(Arrays.asList(prepareEvent, workEvent, restEvent));
        }

        playButtonTouched = true;
    }

    private void onPreviousExercise() {
        updateData("previous");
    }

    private void onNextExercise() {
        updateData("next");
    }
}

