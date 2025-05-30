package com.kaban.kabanplatform.errors.exceptions;

import lombok.Getter;

@Getter
public enum ColumnErrorCode {

    COLUMN_NOT_FOUND("La colonne demandée est introuvable."),
    BOARD_NOT_FOUND("Le board associé est introuvable."),
    TITLE_MISSING("Le titre de la colonne ne peut pas être vide."),
    TITLE_ALREADY_EXISTS_IN_BOARD("Une colonne avec ce titre existe déjà dans ce board."),
    COLUMN_NOT_BELONGS_TO_BOARD("La colonne n'appartient pas au board spécifié."),
    INVALID_UPDATE("La mise à jour de la colonne est invalide."),
    COLUMN_HAS_TASKS("Impossible de supprimer la colonne car elle contient des tâches.");

    private final String message;

    ColumnErrorCode(String message) {
        this.message = message;
    }
}
