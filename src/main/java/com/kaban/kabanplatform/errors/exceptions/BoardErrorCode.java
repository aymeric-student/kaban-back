package com.kaban.kabanplatform.errors.exceptions;

import lombok.Getter;

@Getter
public enum BoardErrorCode {

    BOARD_NOT_FOUND("Le board demandé est introuvable."),
    TITLE_MISSING("Le titre du board ne peut pas être vide."),
    TITLE_ALREADY_EXISTS("Un board avec ce titre existe déjà."),
    INVALID_UPDATE("La mise à jour du board est invalide."),
    BOARD_HAS_COLUMNS("Impossible de supprimer le board car il contient des colonnes.");

    private final String message;

    BoardErrorCode(String message) {
        this.message = message;
    }
}