package com.kaban.kabanplatform.errors.exceptions;


import lombok.Getter;

public class SubTaskException extends RuntimeException {

    @Getter
    private final SubTaskErrorCode code;

    public SubTaskException(SubTaskErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
