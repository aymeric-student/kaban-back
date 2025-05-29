package com.kaban.kabanplatform.tasks;

import com.kaban.kabanplatform.subtasks.SubTasksDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TasksDto {
    private UUID taskId;
    private String title;
    private List<SubTasksDto> subTasks;
}
