package com.stoldo.fitness_app_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.CardViewFragment2;
import com.stoldo.fitness_app_android.service.SaveService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
//        if (savedInstanceState == null) {
////            getSupportFragmentManager().beginTransaction()
////                    .replace(R.id.container, CardViewFragment2.newInstance("Input text"))
////                    .commitNow();
////        }

//        Intent intent = new Intent(this, ExerciseInformationActivity.class);
////        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);

        SaveService saveService = new SaveService();
        Intent saveIntent = new Intent(this, SaveService.class);
        saveService.startService(saveIntent);
//        saveService.testMethod();
    }

    public void onButtonClick(View view) {
    }
}
