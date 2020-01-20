package com.stoldo.fitness_app_android.shared;

import com.stoldo.fitness_app_android.model.SQLSaveException;
import com.stoldo.fitness_app_android.model.data.entity.WorkoutEntity;
import com.stoldo.fitness_app_android.util.OtherUtil;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.fail;

public class OtherUtilUnitTest {

    @Test
    public void isValidIndexTest() {
        int listSize = 12;

        // positive tests
        assertTrue(OtherUtil.isValidIndex(0, listSize));
        assertTrue(OtherUtil.isValidIndex(1, listSize));
        assertTrue(OtherUtil.isValidIndex(8, listSize));
        assertTrue(OtherUtil.isValidIndex(11, listSize));

        // negative tests
        assertFalse(OtherUtil.isValidIndex(-90, listSize));
        assertFalse(OtherUtil.isValidIndex(-1, listSize));
        assertFalse(OtherUtil.isValidIndex(12, listSize));
        assertFalse(OtherUtil.isValidIndex(80, listSize));
    }

    @Test
    public void falseThenThrowTest() {
        try {
            OtherUtil.falseThenThrow(true, new IllegalStateException());
        } catch (Exception e) {
            fail();
        }

        try {
            OtherUtil.falseThenThrow(false, new IllegalStateException());
            fail();
        } catch (Exception e) {}
    }

    @Test
    public void sqlSaveExceptionTest() {
        WorkoutEntity wo = new WorkoutEntity();
        wo.setId(1);

        try {
            throw new SQLSaveException("my_excpetion_test", wo);
        } catch (SQLSaveException sse) {
            assertEquals("my_excpetion_test", sse.getMessage());
            assertEquals(wo, sse.getEntity());
        } catch (Exception e) {
            fail();
        }
    }

    // TODO runGetter() and runSetter() --> Stefano
}
