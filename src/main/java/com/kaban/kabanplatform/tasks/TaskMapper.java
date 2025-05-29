package com.kaban.kabanplatform.tasks;

public class TaskMapper {
    public static TaskEntity toEntity(TasksDto task) {
        return TaskEntity.builder()
                .taskId(task.getTaskId())
                .build();
    }

    public static TasksDto toDto(TaskEntity task) {
        return TasksDto.builder()
                .taskId(task.getTaskId())
                .build();
    }
}
