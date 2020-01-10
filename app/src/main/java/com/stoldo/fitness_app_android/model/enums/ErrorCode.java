package com.stoldo.fitness_app_android.model.enums;

import java.text.MessageFormat;

public enum ErrorCode {
    E1000("Unkown Error"),
    E1001("Casting Exception: Passed param {0} is not an instance of {1}"),
    E1002("Saving of file {0} did not succeed!"),
    E1003("Failed creating table {0}"),
    E1004("Failed deleting entity {0}"),
    E1005("Failed saving entity {0}"),
    E1006("Failed initializing Repository");


    private String errorMsg;

    ErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg(String... arguments) {
        return MessageFormat.format(errorMsg, arguments);
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
