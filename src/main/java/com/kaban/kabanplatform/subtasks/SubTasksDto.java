package com.kaban.kabanplatform.subtasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubTasksDto {
    private UUID subTaskId;
    private String title;
    private boolean completed;
}
