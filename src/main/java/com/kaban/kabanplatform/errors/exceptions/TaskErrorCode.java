package com.kaban.kabanplatform.errors.exceptions;

import lombok.Getter;

@Getter
public enum TaskErrorCode {
    TASK_NOT_FOUND("La tâche demandée est introuvable."),
    COLUMN_NOT_FOUND("La colonne associée est introuvable."),
    BOARD_NOT_FOUND("Le board associé est introuvable."),
    TITLE_MISSING("Le titre de la tâche ne peut pas être vide."),
    TASK_NOT_BELONGS_TO_COLUMN("La tâche n'appartient pas à la colonne spécifiée."),
    INVALID_UPDATE("La mise à jour de la tâche est invalide."),
    TASK_HAS_SUBTASKS("Impossible de supprimer la tâche car elle contient des sous-tâches.");

    private final String message;

    TaskErrorCode(String message) {
        this.message = message;
    }
}

