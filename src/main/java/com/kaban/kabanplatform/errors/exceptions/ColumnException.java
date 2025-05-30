package com.kaban.kabanplatform.errors.exceptions;

import lombok.Getter;

public class ColumnException extends RuntimeException {
    @Getter
    private final ColumnErrorCode code;

    public ColumnException(ColumnErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}

