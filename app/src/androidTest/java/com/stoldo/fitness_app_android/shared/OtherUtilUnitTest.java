package com.stoldo.fitness_app_android.shared;

import com.stoldo.fitness_app_android.model.data.entity.WorkoutEntity;
import com.stoldo.fitness_app_android.util.OtherUtil;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

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
