package com.kaban.kabanplatform.errors.exceptions;

import lombok.Getter;

public class BoardException extends RuntimeException {

    @Getter
    private final BoardErrorCode code;

    public BoardException(BoardErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
