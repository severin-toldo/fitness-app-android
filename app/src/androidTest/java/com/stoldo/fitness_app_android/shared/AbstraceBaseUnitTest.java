package com.stoldo.fitness_app_android.shared;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import com.stoldo.fitness_app_android.service.SingletonService;
import org.junit.Before;


public abstract class AbstraceBaseUnitTest {
    private Context context;
    private boolean initDone = false;

    @Before
    public void setup() throws Exception {
        if (!initDone) {
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            this.context = context;

            SingletonService s = SingletonService.getInstance(context);
            s.instantiateSingletons();

            initDone = true;
        }
    }

    public Context getContext() {
        return context;
    }
}
