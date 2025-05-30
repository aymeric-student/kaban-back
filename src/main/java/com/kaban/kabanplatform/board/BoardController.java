// BoardController.java
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
     */
    @GetMapping
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        List<BoardDto> boards = boardService.getAll();
        return ResponseEntity.ok(boards);
    }

    /**
     * Récupère un board par son ID avec ses colonnes
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable UUID id) {
        BoardDto board = boardService.getById(id);
        return ResponseEntity.ok(board);
    }

    /**
     * Récupère un board par son ID sans ses colonnes (léger)
     */
    @GetMapping("/{id}/light")
    public ResponseEntity<BoardDto> getBoardByIdLight(@PathVariable UUID id) {
        BoardDto board = boardService.getByIdLight(id);
        return ResponseEntity.ok(board);
    }

    /**
     * Crée un nouveau board
     */
    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto) {
        BoardDto createdBoard = boardService.create(boardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    /**
     * Met à jour un board existant
     */
    @PutMapping("/{id}")
    public ResponseEntity<BoardDto> updateBoard(
            @PathVariable UUID id,
            @RequestBody BoardDto boardDto) {
        BoardDto updatedBoard = boardService.update(id, boardDto);
        return ResponseEntity.ok(updatedBoard);
    }

    /**
     * Supprime un board
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable UUID id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Compte le nombre de colonnes dans un board
     */
    @GetMapping("/{id}/columns/count")
    public ResponseEntity<Long> countColumnsByBoard(@PathVariable UUID id) {
        long count = boardService.countColumnsByBoard(id);
        return ResponseEntity.ok(count);
    }

    /**
     * Recherche des boards par titre
     */
    @GetMapping("/search")
    public ResponseEntity<List<BoardDto>> searchBoardsByTitle(@RequestParam String title) {
        List<BoardDto> boards = boardService.searchBoardsByTitle(title);
        return ResponseEntity.ok(boards);
    }

    /**
     * Duplique un board (avec ses colonnes mais sans les tâches)
     */
    @PostMapping("/{id}/duplicate")
    public ResponseEntity<BoardDto> duplicateBoard(
            @PathVariable UUID id,
            @RequestParam(required = false) String newTitle) {
        BoardDto duplicatedBoard = boardService.duplicate(id, newTitle);
        return ResponseEntity.status(HttpStatus.CREATED).body(duplicatedBoard);
    }
}