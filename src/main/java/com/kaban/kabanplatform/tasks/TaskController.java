package com.kaban.kabanplatform.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ==================== ROUTES SPÉCIFIQUES À LA COLONNE ====================

    /**
     * Récupère toutes les tâches d'une colonne spécifique
     * GET /api/boards/{boardId}/columns/{columnId}/tasks
     */
    @GetMapping("/{boardId}/columns/{columnId}/tasks")
    public ResponseEntity<List<TasksDto>> getTasksByColumn(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId) {
        List<TasksDto> tasks = taskService.getTasksByColumn(boardId, columnId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Récupère une tâche spécifique d'une colonne
     * GET /api/boards/{boardId}/columns/{columnId}/tasks/{taskId}
     */
    @GetMapping("/{boardId}/columns/{columnId}/tasks/{taskId}")
    public ResponseEntity<TasksDto> getTaskByColumnAndId(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId,
            @PathVariable UUID taskId) {
        TasksDto task = taskService.getTaskByColumnAndId(boardId, columnId, taskId);
        return ResponseEntity.ok(task);
    }

    /**
     * Crée une nouvelle tâche dans une colonne spécifique
     * POST /api/boards/{boardId}/columns/{columnId}/tasks
     */
    @PostMapping("/{boardId}/columns/{columnId}/tasks")
    public ResponseEntity<TasksDto> createTaskInColumn(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId,
            @RequestBody TasksDto taskDto) {
        TasksDto createdTask = taskService.create(boardId, columnId, taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    /**
     * Met à jour une tâche dans une colonne spécifique
     * PUT /api/boards/{boardId}/columns/{columnId}/tasks/{taskId}
     */
    @PutMapping("/{boardId}/columns/{columnId}/tasks/{taskId}")
    public ResponseEntity<TasksDto> updateTaskInColumn(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId,
            @PathVariable UUID taskId,
            @RequestBody TasksDto taskDto) {
        TasksDto updatedTask = taskService.updateTaskInColumn(boardId, columnId, taskId, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Supprime une tâche d'une colonne spécifique
     * DELETE /api/boards/{boardId}/columns/{columnId}/tasks/{taskId}
     */
    @DeleteMapping("/{boardId}/columns/{columnId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTaskFromColumn(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId,
            @PathVariable UUID taskId) {
        taskService.deleteTaskFromColumn(boardId, columnId, taskId);
        return ResponseEntity.noContent().build();
    }

    // ==================== ROUTES SPÉCIFIQUES AU BOARD (toutes colonnes) ====================

    /**
     * Récupère toutes les tâches d'un board (toutes colonnes)
     * GET /api/boards/{boardId}/tasks
     */
    @GetMapping("/{boardId}/tasks")
    public ResponseEntity<List<TasksDto>> getTasksByBoard(@PathVariable UUID boardId) {
        List<TasksDto> tasks = taskService.getTasksByBoard(boardId);
        return ResponseEntity.ok(tasks);
    }
}
