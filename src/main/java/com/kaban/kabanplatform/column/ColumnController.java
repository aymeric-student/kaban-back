package com.kaban.kabanplatform.column;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/columns")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    /**
     * Récupère toutes les colonnes
     */
    @GetMapping
    public ResponseEntity<List<ColumnDto>> getAllColumns() {
        List<ColumnDto> columns = columnService.getAll();
        return ResponseEntity.ok(columns);
    }

    /**
     * Récupère une colonne par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ColumnDto> getColumnById(@PathVariable UUID id) {
        ColumnDto column = columnService.getById(id);
        return ResponseEntity.ok(column);
    }

    /**
     * Récupère toutes les colonnes d'un board spécifique
     */
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<ColumnDto>> getColumnsByBoard(@PathVariable UUID boardId) {
        List<ColumnDto> columns = columnService.getColumnsByBoard(boardId);
        return ResponseEntity.ok(columns);
    }

    /**
     * Crée une nouvelle colonne dans un board
     */
    @PostMapping("/board/{boardId}")
    public ResponseEntity<ColumnDto> createColumn(
            @PathVariable UUID boardId,
            @RequestBody ColumnDto columnDto) {
        ColumnDto createdColumn = columnService.create(boardId, columnDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdColumn);
    }

    /**
     * Met à jour une colonne existante
     */
    @PutMapping("/{id}")
    public ResponseEntity<ColumnDto> updateColumn(
            @PathVariable UUID id,
            @RequestBody ColumnDto columnDto) {
        ColumnDto updatedColumn = columnService.update(id, columnDto);
        return ResponseEntity.ok(updatedColumn);
    }

    /**
     * Déplace une colonne vers un autre board
     */
    @PutMapping("/{id}/move-to-board/{boardId}")
    public ResponseEntity<ColumnDto> moveColumnToBoard(
            @PathVariable UUID id,
            @PathVariable UUID boardId) {
        ColumnDto movedColumn = columnService.moveToBoard(id, boardId);
        return ResponseEntity.ok(movedColumn);
    }

    /**
     * Supprime une colonne
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable UUID id) {
        columnService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Compte le nombre de colonnes dans un board
     */
    @GetMapping("/board/{boardId}/count")
    public ResponseEntity<Long> countColumnsByBoard(@PathVariable UUID boardId) {
        long count = columnService.countColumnsByBoard(boardId);
        return ResponseEntity.ok(count);
    }

    /**
     * Recherche des colonnes par titre
     */
    @GetMapping("/search")
    public ResponseEntity<List<ColumnDto>> searchColumnsByTitle(@RequestParam String title) {
        List<ColumnDto> columns = columnService.searchColumnsByTitle(title);
        return ResponseEntity.ok(columns);
    }
}