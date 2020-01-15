package com.stoldo.fitness_app_android.shared.util;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.stoldo.fitness_app_android.AbstraceBaseUnitTest;
import com.stoldo.fitness_app_android.service.ExerciseService;
import com.stoldo.fitness_app_android.service.SingletonService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

// fuck yeah this works --> can be used for android tests.

@RunWith(AndroidJUnit4.class)
public class OtherUtilUnitTest extends AbstraceBaseUnitTest {
    @Test
    public void serviceTest() {
        Log.d("MYDEBUG", "Hello World");
    }
}
