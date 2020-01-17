package com.stoldo.fitness_app_android.shared;

import android.util.Log;

import com.stoldo.fitness_app_android.util.OtherUtil;

import org.junit.Assert;
import org.junit.Test;

// fuck yeah this works --> can be used for android tests.

public class OtherUtilUnitTest extends AbstraceBaseUnitTest {
    @Test
    public void serviceTest() {
        Assert.assertEquals(true, true);
        Log.d("MYDEBUG", "Hello World");
    }

    @Test
    public void isValidIndexTestt() {
        int listSize = 12;

        // positive tests
        Assert.assertEquals(true, OtherUtil.isValidIndex(0, listSize));
        Assert.assertEquals(true, OtherUtil.isValidIndex(1, listSize));
        Assert.assertEquals(true, OtherUtil.isValidIndex(8, listSize));
        Assert.assertEquals(true, OtherUtil.isValidIndex(11, listSize));

        // negative tests
        Assert.assertEquals(false, OtherUtil.isValidIndex(-90, listSize));
        Assert.assertEquals(false, OtherUtil.isValidIndex(-1, listSize));
        Assert.assertEquals(false, OtherUtil.isValidIndex(12, listSize));
        Assert.assertEquals(false, OtherUtil.isValidIndex(80, listSize));
    }

    // TODO add runSetter() & runGetter()
    // TODO try mocking context again and test dp to pixel etc.
// TODO also test the own exception
// TODO evtl repo testing with mocking and then return?
}
