package com.kaban.kabanplatform.column;

import com.kaban.kabanplatform.tasks.TasksDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDto {
    private UUID columnId;
    private List<TasksDto> tasks;
    private String title;
    private UUID boardId;
}
