package com.kaban.kabanplatform.subtasks;

import com.kaban.kabanplatform.errors.exceptions.SubTaskErrorCode;
import com.kaban.kabanplatform.errors.exceptions.SubTaskException;
import com.kaban.kabanplatform.tasks.TaskEntity;
import com.kaban.kabanplatform.tasks.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubTaskService {

    private final SubTaskRepository subTaskRepository;
    private final TaskRepository taskRepository;

    public List<SubTasksDto> getAll() {
        return subTaskRepository.findAll().stream()
                .map(SubTaskMapper::toDto)
                .toList();
    }

    public SubTasksDto getById(UUID id) {
        return subTaskRepository.findById(id)
                .map(SubTaskMapper::toDto)
                .orElseThrow(() -> new SubTaskException(SubTaskErrorCode.SUBTASK_NOT_FOUND));
    }

    public SubTasksDto create(UUID taskId, SubTasksDto dto) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new SubTaskException(SubTaskErrorCode.TASK_NOT_FOUND));

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new SubTaskException(SubTaskErrorCode.TITLE_MISSING);
        }

        SubTaskEntity entity = SubTaskMapper.toEntity(dto, task);
        entity.setTask(task);
        return SubTaskMapper.toDto(subTaskRepository.save(entity));
    }

    public SubTasksDto update(UUID id, SubTasksDto dto) {
        SubTaskEntity entity = subTaskRepository.findById(id)
                .orElseThrow(() -> new SubTaskException(SubTaskErrorCode.SUBTASK_NOT_FOUND));

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new SubTaskException(SubTaskErrorCode.TITLE_MISSING);
        }

        entity.setTitle(dto.getTitle());
        entity.setCompleted(dto.isCompleted());

        return SubTaskMapper.toDto(subTaskRepository.save(entity));
    }

    public void delete(UUID id) {
        if (!subTaskRepository.existsById(id)) {
            throw new SubTaskException(SubTaskErrorCode.SUBTASK_NOT_FOUND);
        }
        subTaskRepository.deleteById(id);
    }
}
