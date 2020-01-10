package com.stoldo.fitness_app_android.repository;

import android.content.Context;

import com.stoldo.fitness_app_android.model.abstracts.AbstractBaseRepository;
import com.stoldo.fitness_app_android.model.annotaions.Singleton;
import com.stoldo.fitness_app_android.model.data.entity.WorkoutEntity;

import java.sql.SQLException;

@Singleton
public class WorkoutRepository extends AbstractBaseRepository<WorkoutEntity> {

    private WorkoutRepository(Context context) throws SQLException {
        super(context, WorkoutEntity.class);
    }
}
