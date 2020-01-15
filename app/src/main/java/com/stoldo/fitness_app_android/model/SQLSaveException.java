package com.stoldo.fitness_app_android.model;

import com.stoldo.fitness_app_android.model.interfaces.Entity;

import java.sql.SQLException;

import lombok.Getter;

/**
 * Is thrown when an error occures while saving an entity.
 * It contains the failed entity so information can be passed easier to the ui.
 * */
public class SQLSaveException extends SQLException {
    @Getter
    private Entity entity;

    /**
     * @param message Error Message
     * @param entity Failed Entity
     * */
    public SQLSaveException(String message, Entity entity) {
        super(message);
        this.entity = entity;
    }
}
