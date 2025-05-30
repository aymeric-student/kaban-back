package com.kaban.kabanplatform.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Récupère toutes les tâches
     */
    @GetMapping
    public ResponseEntity<List<TasksDto>> getAllTasks() {
        List<TasksDto> tasks = taskService.getAll();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Récupère une tâche par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TasksDto> getTaskById(@PathVariable UUID id) {
        TasksDto task = taskService.getById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Récupère toutes les tâches d'une colonne
     */
    @GetMapping("/column/{columnId}")
    public ResponseEntity<List<TasksDto>> getTasksByColumn(@PathVariable UUID columnId) {
        List<TasksDto> tasks = taskService.getTasksByColumn(columnId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Crée une nouvelle tâche dans une colonne
     */
    @PostMapping("/column/{columnId}")
    public ResponseEntity<TasksDto> createTask(
            @PathVariable UUID columnId,
            @RequestBody TasksDto taskDto) {
        TasksDto createdTask = taskService.create(columnId, taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    /**
     * Met à jour une tâche existante
     */
    @PutMapping("/{id}")
    public ResponseEntity<TasksDto> updateTask(
            @PathVariable UUID id,
            @RequestBody TasksDto taskDto) {
        TasksDto updatedTask = taskService.update(id, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Déplace une tâche vers une autre colonne
     */
    @PutMapping("/{id}/move-to-column/{columnId}")
    public ResponseEntity<TasksDto> moveTaskToColumn(
            @PathVariable UUID id,
            @PathVariable UUID columnId) {
        TasksDto movedTask = taskService.moveToColumn(id, columnId);
        return ResponseEntity.ok(movedTask);
    }

    /**
     * Supprime une tâche
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}