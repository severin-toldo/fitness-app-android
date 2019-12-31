package com.stoldo.fitness_app_android.model.data.events;

import com.stoldo.fitness_app_android.model.enums.ActionType;
import com.stoldo.fitness_app_android.model.interfaces.Event;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class ActionEvent implements Event {
    private Boolean editMode = false;
    private ActionType actionType;
}
