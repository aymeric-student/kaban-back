package com.kaban.kabanplatform.board;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * Récupère tous les boards
     * GET /api/boards
     */
    @GetMapping
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        List<BoardDto> boards = boardService.getAll();
        return ResponseEntity.ok(boards);
    }

    /**
     * Récupère un board par son ID avec ses colonnes
     * GET /api/boards/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable UUID id) {
        BoardDto board = boardService.getById(id);
        return ResponseEntity.ok(board);
    }

    /**
     * Récupère un board par son ID sans ses colonnes (léger)
     * GET /api/boards/{id}/light
     */
    @GetMapping("/{id}/light")
    public ResponseEntity<BoardDto> getBoardByIdLight(@PathVariable UUID id) {
        BoardDto board = boardService.getByIdLight(id);
        return ResponseEntity.ok(board);
    }

    /**
     * Récupère un board par son ID AVEC ses tâches (complet)
     * GET /api/boards/{id}/with-tasks
     */
    @GetMapping("/{id}/columns/with-tasks")
    public ResponseEntity<BoardDto> getBoardByIdWithTasks(@PathVariable UUID id) {
        BoardDto board = boardService.getByIdWithTasks(id);
        return ResponseEntity.ok(board);
    }

    /**
     * Crée un nouveau board
     * POST /api/boards
     */
    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto) {
        BoardDto createdBoard = boardService.create(boardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    /**
     * Met à jour un board existant
     * PUT /api/boards/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<BoardDto> updateBoard(
            @PathVariable UUID id,
            @RequestBody BoardDto boardDto) {
        BoardDto updatedBoard = boardService.update(id, boardDto);
        return ResponseEntity.ok(updatedBoard);
    }

    /**
     * Compte le nombre de colonnes dans un board
     * GET /api/boards/{id}/columns/count
     */
    @GetMapping("/{id}/columns/count")
    public ResponseEntity<Long> countColumnsByBoard(@PathVariable UUID id) {
        long count = boardService.countColumnsByBoard(id);
        return ResponseEntity.ok(count);
    }
}