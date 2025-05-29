package com.kaban.kabanplatform.subtasks;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subtasks")
@RequiredArgsConstructor
public class SubTaskController {

    private final SubTaskService subTaskService;

    @GetMapping
    public List<SubTasksDto> getAll() {
        return subTaskService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubTasksDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(subTaskService.getById(id));
    }

    @PostMapping("/task/{taskId}")
    public ResponseEntity<SubTasksDto> create(@PathVariable UUID taskId, @RequestBody SubTasksDto dto) {
        return ResponseEntity.ok(subTaskService.create(taskId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubTasksDto> update(@PathVariable UUID id, @RequestBody SubTasksDto dto) {
        return ResponseEntity.ok(subTaskService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        subTaskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
