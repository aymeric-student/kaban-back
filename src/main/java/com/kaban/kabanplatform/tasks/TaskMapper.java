package com.kaban.kabanplatform.tasks;

import com.kaban.kabanplatform.subtasks.SubTaskMapper;
import com.kaban.kabanplatform.subtasks.SubTasksDto;

import java.util.List;

public class TaskMapper {

    /**
     * Convertit une TaskEntity en TasksDto (version complète avec sous-tâches)
     */
    public static TasksDto toDto(TaskEntity task) {
        List<SubTasksDto> subTasks = List.of();

        // Si les sous-tâches sont chargées, les mapper
        if (task.getSubTaskEntities() != null) {
            subTasks = task.getSubTaskEntities().stream()
                    .map(SubTaskMapper::toDto)
                    .toList();
        }

        return TasksDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .subTasks(subTasks)
                .build();
    }

    /**
     * Convertit une TaskEntity en TasksDto (version sans sous-tâches)
     */
    public static TasksDto toDtoWithoutSubTasks(TaskEntity task) {
        return TasksDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .subTasks(List.of())
                .build();
    }

    /**
     * Convertit un TasksDto en TaskEntity (pour création/mise à jour)
     */
    public static TaskEntity toEntity(TasksDto dto) {
        return TaskEntity.builder()
                .taskId(dto.getTaskId())
                .title(dto.getTitle())
                .build();
    }
}