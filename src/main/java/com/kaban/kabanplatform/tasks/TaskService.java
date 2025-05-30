package com.kaban.kabanplatform.tasks;

import com.kaban.kabanplatform.board.BoardRepository;
import com.kaban.kabanplatform.column.ColumnEntity;
import com.kaban.kabanplatform.column.ColumnRepository;
import com.kaban.kabanplatform.errors.global.BadRequestException;
import com.kaban.kabanplatform.errors.global.NotFoundException;
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
    private final BoardRepository boardRepository;

    // ==================== MÉTHODES GLOBALES ====================

    @Transactional(readOnly = true)
    public List<TasksDto> getAll() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TasksDto getById(UUID id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche avec l'ID " + id + " introuvable"));
        return TaskMapper.toDto(task);
    }

    // ==================== MÉTHODES SPÉCIFIQUES À LA COLONNE ====================

    @Transactional(readOnly = true)
    public List<TasksDto> getTasksByColumn(UUID boardId, UUID columnId) {
        validateBoardExists(boardId);
        validateColumnBelongsToBoard(columnId, boardId);

        return taskRepository.findByColumnColumnId(columnId).stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TasksDto getTaskByColumnAndId(UUID boardId, UUID columnId, UUID taskId) {
        validateBoardExists(boardId);
        validateColumnBelongsToBoard(columnId, boardId);

        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tâche avec l'ID " + taskId + " introuvable"));

        // Vérifier que la tâche appartient bien à la colonne
        if (!task.getColumn().getColumnId().equals(columnId)) {
            throw new BadRequestException("La tâche " + taskId + " n'appartient pas à la colonne " + columnId);
        }

        return TaskMapper.toDto(task);
    }

    public TasksDto create(UUID boardId, UUID columnId, TasksDto dto) {
        validateBoardExists(boardId);
        ColumnEntity column = validateColumnBelongsToBoard(columnId, boardId);

        validateTaskTitle(dto.getTitle());

        TaskEntity task = TaskEntity.builder()
                .title(dto.getTitle().trim())
                .column(column)
                .status(false) // Par défaut non terminée
                .build();

        TaskEntity savedTask = taskRepository.save(task);
        return TaskMapper.toDto(savedTask);
    }

    public TasksDto updateTaskInColumn(UUID boardId, UUID columnId, UUID taskId, TasksDto dto) {
        validateBoardExists(boardId);
        validateColumnBelongsToBoard(columnId, boardId);

        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tâche avec l'ID " + taskId + " introuvable"));

        // Vérifier que la tâche appartient bien à la colonne
        if (!task.getColumn().getColumnId().equals(columnId)) {
            throw new BadRequestException("La tâche " + taskId + " n'appartient pas à la colonne " + columnId);
        }

        validateTaskTitle(dto.getTitle());
        task.setTitle(dto.getTitle().trim());

        TaskEntity updatedTask = taskRepository.save(task);
        return TaskMapper.toDto(updatedTask);
    }

    public void deleteTaskFromColumn(UUID boardId, UUID columnId, UUID taskId) {
        validateBoardExists(boardId);
        validateColumnBelongsToBoard(columnId, boardId);

        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tâche avec l'ID " + taskId + " introuvable"));

        // Vérifier que la tâche appartient bien à la colonne
        if (!task.getColumn().getColumnId().equals(columnId)) {
            throw new BadRequestException("La tâche " + taskId + " n'appartient pas à la colonne " + columnId);
        }

        taskRepository.deleteById(taskId);
    }

    // ==================== MÉTHODES SPÉCIFIQUES AU BOARD ====================

    @Transactional(readOnly = true)
    public List<TasksDto> getTasksByBoard(UUID boardId) {
        validateBoardExists(boardId);

        // Récupérer toutes les colonnes du board
        List<ColumnEntity> columns = columnRepository.findByBoardBoardId(boardId);

        // Récupérer toutes les tâches de toutes les colonnes
        return columns.stream()
                .flatMap(column -> taskRepository.findByColumnColumnId(column.getColumnId()).stream())
                .map(TaskMapper::toDto)
                .toList();
    }

    // ==================== MÉTHODES DE VALIDATION PRIVÉES ====================

    private void validateBoardExists(UUID boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new NotFoundException("Board avec l'ID " + boardId + " introuvable");
        }
    }

    private ColumnEntity validateColumnBelongsToBoard(UUID columnId, UUID boardId) {
        ColumnEntity column = columnRepository.findById(columnId)
                .orElseThrow(() -> new NotFoundException("Colonne avec l'ID " + columnId + " introuvable"));

        if (!column.getBoard().getBoardId().equals(boardId)) {
            throw new BadRequestException("La colonne " + columnId + " n'appartient pas au board " + boardId);
        }

        return column;
    }

    private void validateTaskTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException("Le titre de la tâche ne peut pas être vide");
        }
    }

}