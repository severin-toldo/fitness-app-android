package com.stoldo.fitness_app_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.fragments.CardViewFragment2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CardViewFragment2.newInstance("Input text"))
                    .commitNow();
        }
    }

    public void onButtonClick(View view) {
    }
}
