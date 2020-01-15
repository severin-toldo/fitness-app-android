package com.stoldo.fitness_app_android.shared;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.stoldo.fitness_app_android.service.ExerciseService;
import com.stoldo.fitness_app_android.service.SingletonService;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public abstract class AbstraceBaseUnitTest {
    private Context context;

//    @BeforeClass
//    public void setup() throws Exception {
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        this.context = context;
//
//        SingletonService s = SingletonService.getInstance(context);
//        s.instantiateSingletons();
//        ExerciseService es = (ExerciseService) s.getSingletonByClass(ExerciseService.class);
//        Log.d("MYDEBUG: ", "Outcome" + es.toString());
//    }
}
