package com.stoldo.fitness_app_android.shared.util;


import com.stoldo.fitness_app_android.BaseUnitTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO try mocking context again and test dp to pixel etc.
// TODO also test the own exception
// TODO evtl repo testing with mocking and then return?
public class OtherUtilUnitTest extends BaseUnitTest {
    @Test
    public void isValidIndexTest() {
        int listSize = 12;

        // positive tests
        assertEquals(true, OtherUtil.isValidIndex(0, listSize));
        assertEquals(true, OtherUtil.isValidIndex(1, listSize));
        assertEquals(true, OtherUtil.isValidIndex(8, listSize));
        assertEquals(true, OtherUtil.isValidIndex(11, listSize));

        // negative tests
        assertEquals(false, OtherUtil.isValidIndex(-90, listSize));
        assertEquals(false, OtherUtil.isValidIndex(-1, listSize));
        assertEquals(false, OtherUtil.isValidIndex(12, listSize));
        assertEquals(false, OtherUtil.isValidIndex(80, listSize));
    }

    // TODO add runSetter() & runGetter()




//    public static void falseThenThrow(boolean b, Exception e) throws Exception {
//        if (!b) {
//            LogUtil.logError(ErrorCode.E1000.getErrorMsg(), OtherUtil.class, e);
//            throw e;
//        }
//    }
}
