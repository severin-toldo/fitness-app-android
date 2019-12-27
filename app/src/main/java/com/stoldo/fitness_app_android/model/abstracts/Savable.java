package com.stoldo.fitness_app_android.model.abstracts;

import java.io.File;

import lombok.AccessLevel;
import lombok.Setter;

@lombok.Setter
@lombok.Getter
public abstract class Savable {
    private Integer id;

    @Setter(AccessLevel.NONE)
    private String dirName = null;

    public Savable(String dirName) {
        this.dirName = dirName;
    }

    // TODO remove this.
    // TODO add an abstract mehtod get type with enum of savable type. this enum needs konstructore in which the path is. or something like that.
    // TODO think of a good solution
    public final File getSaveDir(String basePath) {
        return new File(basePath + dirName + "_" + id + File.separator);
    }
}
