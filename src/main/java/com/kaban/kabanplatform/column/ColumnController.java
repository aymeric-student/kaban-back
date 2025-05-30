package com.kaban.kabanplatform.column;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    // ==================== ROUTES SPÉCIFIQUES AU BOARD ====================

    /**
     * Récupère toutes les colonnes d'un board spécifique
     * GET /api/boards/{boardId}/columns
     */
    @GetMapping("/{boardId}/columns")
    public ResponseEntity<List<ColumnDto>> getColumnsByBoard(@PathVariable UUID boardId) {
        List<ColumnDto> columns = columnService.getColumnsByBoard(boardId);
        return ResponseEntity.ok(columns);
    }

    /**
     * Crée une nouvelle colonne dans un board spécifique
     * POST /api/boards/{boardId}/columns
     */
    @PostMapping("/{boardId}/columns")
    public ResponseEntity<ColumnDto> createColumnInBoard(
            @PathVariable UUID boardId,
            @RequestBody ColumnDto columnDto) {
        ColumnDto createdColumn = columnService.create(boardId, columnDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdColumn);
    }

    /**
     * Compte le nombre de colonnes dans un board
     * GET /api/boards/{boardId}/columns/count
     */
    @GetMapping("/{boardId}/columns/count")
    public ResponseEntity<Long> countColumnsByBoard(@PathVariable UUID boardId) {
        long count = columnService.countColumnsByBoard(boardId);
        return ResponseEntity.ok(count);
    }
}