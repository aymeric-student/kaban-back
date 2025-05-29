package com.kaban.kabanplatform.tasks;

import com.kaban.kabanplatform.column.ColumnEntity;
import com.kaban.kabanplatform.column.ColumnRepository;
import com.kaban.kabanplatform.errors.global.NotFoundException;
import com.kaban.kabanplatform.subtasks.SubTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final ColumnRepository columnRepository;

    @Transactional(readOnly = true)
    public List<TasksDto> getAll() {
        return taskRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TasksDto getById(UUID id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche avec l'ID " + id + " introuvable"));
        return mapToDto(task);
    }

    public TasksDto create(UUID columnId, TasksDto dto) {
        ColumnEntity column = columnRepository.findById(columnId)
                .orElseThrow(() -> new NotFoundException("Colonne avec l'ID " + columnId + " introuvable"));

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Le titre de la tâche ne peut pas être vide");
        }

        TaskEntity task = TaskEntity.builder()
                .title(dto.getTitle())
                .column(column)
                .build();

        TaskEntity savedTask = taskRepository.save(task);
        return mapToDto(savedTask);
    }

    public TasksDto update(UUID id, TasksDto dto) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche avec l'ID " + id + " introuvable"));

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Le titre de la tâche ne peut pas être vide");
        }

        task.setTitle(dto.getTitle());

        TaskEntity updatedTask = taskRepository.save(task);
        return mapToDto(updatedTask);
    }

    public TasksDto moveToColumn(UUID taskId, UUID newColumnId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tâche avec l'ID " + taskId + " introuvable"));

        ColumnEntity newColumn = columnRepository.findById(newColumnId)
                .orElseThrow(() -> new NotFoundException("Colonne avec l'ID " + newColumnId + " introuvable"));

        task.setColumn(newColumn);
        TaskEntity updatedTask = taskRepository.save(task);
        return mapToDto(updatedTask);
    }

    public void delete(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Tâche avec l'ID " + id + " introuvable");
        }
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TasksDto> getTasksByColumn(UUID columnId) {
        return taskRepository.findByColumnColumnId(columnId).stream()
                .map(this::mapToDto)
                .toList();
    }

    private TasksDto mapToDto(TaskEntity task) {
        return TasksDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .subTasks(task.getSubTaskEntities() != null ?
                        task.getSubTaskEntities().stream()
                                .map(SubTaskMapper::toDto)
                                .toList() : List.of())
                .build();
    }
}