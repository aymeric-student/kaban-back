package com.kaban.kabanplatform.column;

import com.kaban.kabanplatform.tasks.TasksDto;
import com.kaban.kabanplatform.tasks.TaskMapper;

import java.util.List;

public class ColumnMapper {

    /**
     * Convertit une ColumnEntity en ColumnDto (version complète avec tâches)
     */
    public static ColumnDto toDtoWithTasks(ColumnEntity column) {
        List<TasksDto> tasks = List.of(); // Par défaut, liste vide

        // Si les tâches sont chargées, les mapper
        if (column.getTasks() != null) {
            tasks = column.getTasks().stream()
                    .map(TaskMapper::toDto) // Utilise le TaskMapper
                    .toList();
        }

        return ColumnDto.builder()
                .columnId(column.getColumnId())
                .title(column.getTitle())
                .boardId(column.getBoard().getBoardId())
                .tasks(tasks)
                .build();
    }

    /**
     * Convertit une ColumnEntity en ColumnDto (version sans tâches)
     */
    public static ColumnDto toDtoWithoutTasks(ColumnEntity column) {
        return ColumnDto.builder()
                .columnId(column.getColumnId())
                .title(column.getTitle())
                .boardId(column.getBoard().getBoardId())
                .tasks(List.of()) // Liste vide pour version légère
                .build();
    }

    /**
     * Convertit un ColumnDto en ColumnEntity (pour création/mise à jour)
     */
    public static ColumnEntity toEntity(ColumnDto dto) {
        return ColumnEntity.builder()
                .columnId(dto.getColumnId())
                .title(dto.getTitle())
                .build();
    }
}