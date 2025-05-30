package com.kaban.kabanplatform.errors.exceptions;

import lombok.Getter;

public class TaskException extends RuntimeException {
    @Getter
    private final TaskErrorCode code;

    public TaskException(TaskErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
