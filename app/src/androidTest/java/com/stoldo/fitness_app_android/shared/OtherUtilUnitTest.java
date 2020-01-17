package com.stoldo.fitness_app_android.shared;

import com.stoldo.fitness_app_android.util.OtherUtil;

import org.junit.Assert;
import org.junit.Test;

public class OtherUtilUnitTest {
    @Test
    public void isValidIndexTest() {
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

    // TODO runGetter() and runSetter()
}
