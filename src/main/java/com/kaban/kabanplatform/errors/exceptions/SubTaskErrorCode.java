package com.kaban.kabanplatform.errors.exceptions;

import lombok.Getter;

@Getter
public enum SubTaskErrorCode {

    SUBTASK_NOT_FOUND("La sous-tâche demandée est introuvable."),
    TASK_NOT_FOUND("La tâche associée est introuvable."),
    TITLE_MISSING("Le titre de la sous-tâche ne peut pas être vide."),
    INVALID_UPDATE("La mise à jour de la sous-tâche est invalide."),
    ALREADY_COMPLETED("La sous-tâche est déjà marquée comme complétée.");

    private final String message;

    SubTaskErrorCode(String message) {
        this.message = message;
    }
}
