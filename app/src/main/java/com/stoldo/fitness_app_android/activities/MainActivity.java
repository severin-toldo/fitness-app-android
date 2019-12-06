package com.stoldo.fitness_app_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.stoldo.fitness_app_android.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, ElementListFragment.newInstance("Input text"))
//                    .commitNow();
//        }
    }
}
