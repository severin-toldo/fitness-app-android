package com.stoldo.fitness_app_android.model.enums;

import java.text.MessageFormat;

public enum ErrorCode {
    E1000("Unkown Error"),
    E1001("Casting Exception: Passed param {0} is not an instance of {1}");

    private String errorMsg;

    ErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg(String... arguments) {
        return MessageFormat.format(errorMsg, arguments);
    }
}
