package com.kaban.kabanplatform.subtasks;

import com.kaban.kabanplatform.tasks.TaskEntity;

public class SubTaskMapper {

    public static SubTaskEntity toEntity(SubTasksDto dto, TaskEntity task) {
        return SubTaskEntity.builder()
                .title(dto.getTitle())
                .completed(dto.isCompleted())
                .build();
    }

    public static SubTasksDto toDto(SubTaskEntity entity) {
        return SubTasksDto.builder()
                .subTaskId(entity.getSubTaskId())
                .title(entity.getTitle())
                .completed(entity.isCompleted())
                .build();
    }
}
