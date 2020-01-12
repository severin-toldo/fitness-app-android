package com.stoldo.fitness_app_android.model.enums;

import java.text.MessageFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorCode {
    E1000("Unkown Error"),
    E1001("Casting Exception: Passed param {0} is not an instance of {1}"),
    E1002("Error during invocation of method {0}"),
    E1003("Failed creating table {0}"),
    E1004("Failed deleting entity {0}"),
    E1005("Failed inserting entity {0}"),
    E1006("Failed initializing Repository"),
    E1007("Failed updating entity {0}");

    @Getter
    private String errorMsg;

    public String getErrorMsg(String... arguments) {
        return MessageFormat.format(errorMsg, arguments);
    }
}
