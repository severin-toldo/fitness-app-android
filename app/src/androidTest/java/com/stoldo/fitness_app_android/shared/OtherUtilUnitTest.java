package com.stoldo.fitness_app_android.shared;

import com.stoldo.fitness_app_android.model.SQLSaveException;
import com.stoldo.fitness_app_android.model.data.entity.WorkoutEntity;
import com.stoldo.fitness_app_android.util.OtherUtil;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
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
    @Test
    public void runGetterTest() throws NoSuchFieldException {
        WorkoutEntity workout = new WorkoutEntity();
        workout.setTitle("testValue");
        workout.setId(12);
        Field title = WorkoutEntity.class.getDeclaredField("title");
        Field id = WorkoutEntity.class.getDeclaredField("id");

        // positive tests
        Assert.assertEquals("testValue", OtherUtil.runGetter(title, workout));
        Assert.assertEquals(12,          OtherUtil.runGetter(id, workout));

        //negative tests
        Assert.assertNotEquals("notTestValue", OtherUtil.runGetter(title, workout));
        Assert.assertNotEquals(20,             OtherUtil.runGetter(id, workout));
    }

    @Test
    public void runSetterTest() throws NoSuchFieldException {
        WorkoutEntity workout = new WorkoutEntity();
        Field title = WorkoutEntity.class.getDeclaredField("title");
        Field id = WorkoutEntity.class.getDeclaredField("id");

        // positive tests
        Assert.assertNull(workout.getTitle()); //bevor
        OtherUtil.runSetter(title, workout, "testValue");
        Assert.assertEquals("testValue", workout.getTitle()); //danach

        Assert.assertNull(workout.getId()); //bevor
        OtherUtil.runSetter(id, workout, 12);
        Assert.assertEquals(12, (int)workout.getId());

        //negative tests
        OtherUtil.runSetter(title, workout, "testtest");
        Assert.assertNotEquals("1234ttt", workout.getTitle());

        OtherUtil.runSetter(id, workout, 5555);
        Assert.assertNotEquals(81, (int)workout.getId());
    }
}
