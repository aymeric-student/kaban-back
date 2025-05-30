package com.kaban.kabanplatform.board;

import com.kaban.kabanplatform.column.ColumnEntity;
import com.kaban.kabanplatform.column.ColumnRepository;
import com.kaban.kabanplatform.column.ColumnMapper;
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
public class BoardService {

    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;

    @Transactional(readOnly = true)
    public List<BoardDto> getAll() {
        return boardRepository.findAllWithColumns().stream()
                .map(BoardMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoardDto getById(UUID id) {
        BoardEntity board = boardRepository.findByIdWithColumns(id)
                .orElseThrow(() -> new NotFoundException("Board avec l'ID " + id + " introuvable"));
        return BoardMapper.toDto(board);
    }

    @Transactional(readOnly = true)
    public BoardDto getByIdLight(UUID id) {
        BoardEntity board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Board avec l'ID " + id + " introuvable"));
        return BoardMapper.toDtoLight(board);
    }

    @Transactional(readOnly = true)
    public BoardDto getByIdWithTasks(UUID id) {
        // Vérifier que le board existe
        if (!boardRepository.existsById(id)) {
            throw new NotFoundException("Board avec l'ID " + id + " introuvable");
        }

        // Récupérer directement les colonnes avec leurs tâches
        List<ColumnEntity> columnsWithTasks = columnRepository.findByBoardBoardIdWithTasks(id);

        if (columnsWithTasks.isEmpty()) {
            BoardEntity board = boardRepository.findById(id).get();
            return BoardMapper.toDtoLight(board);
        }

        // Construire le DTO à partir des colonnes en utilisant les mappers
        BoardEntity board = columnsWithTasks.get(0).getBoard();
        board.setColumns(columnsWithTasks); // Assigner les colonnes chargées

        return BoardMapper.toDtoWithTasks(board);
    }

    public BoardDto create(BoardDto boardDto) {
        // Validation du titre
        if (boardDto.getTitle() == null || boardDto.getTitle().isBlank()) {
            throw new BadRequestException("Le titre du board ne peut pas être vide");
        }

        // Vérification de l'unicité du titre
        if (boardRepository.existsByTitle(boardDto.getTitle().trim())) {
            throw new BadRequestException("Un board avec ce titre existe déjà");
        }

        BoardEntity board = BoardEntity.builder()
                .title(boardDto.getTitle().trim())
                .build();

        BoardEntity savedBoard = boardRepository.save(board);
        return BoardMapper.toDtoLight(savedBoard);
    }

    public BoardDto update(UUID id, BoardDto boardDto) {
        BoardEntity board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Board avec l'ID " + id + " introuvable"));

        // Validation du titre
        if (boardDto.getTitle() == null || boardDto.getTitle().isBlank()) {
            throw new BadRequestException("Le titre du board ne peut pas être vide");
        }

        board.setTitle(boardDto.getTitle().trim());
        BoardEntity updatedBoard = boardRepository.save(board);
        return BoardMapper.toDtoLight(updatedBoard);
    }

    public void delete(UUID id) {
        BoardEntity board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Board avec l'ID " + id + " introuvable"));

        // Vérification que le board n'a pas de colonnes (protection des données)
        long columnCount = columnRepository.countByBoardBoardId(id);
        if (columnCount > 0) {
            throw new BadRequestException("Impossible de supprimer le board car il contient des colonnes. " +
                    "Veuillez d'abord supprimer toutes les colonnes.");
        }

        boardRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countColumnsByBoard(UUID boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new NotFoundException("Board avec l'ID " + boardId + " introuvable");
        }
        return columnRepository.countByBoardBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public List<BoardDto> searchBoardsByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException("Le terme de recherche ne peut pas être vide");
        }
        return boardRepository.findByTitleContainingIgnoreCase(title.trim()).stream()
                .map(BoardMapper::toDtoLight)
                .toList();
    }

    public BoardDto duplicate(UUID originalBoardId, String newTitle) {
        // Récupérer le board original avec ses colonnes
        BoardEntity originalBoard = boardRepository.findByIdWithColumns(originalBoardId)
                .orElseThrow(() -> new NotFoundException("Board avec l'ID " + originalBoardId + " introuvable"));

        // Définir le titre du nouveau board
        String duplicatedTitle = (newTitle != null && !newTitle.isBlank())
                ? newTitle.trim()
                : originalBoard.getTitle() + " (Copie)";

        // Vérifier l'unicité du nouveau titre
        if (boardRepository.existsByTitle(duplicatedTitle)) {
            throw new BadRequestException("Un board avec le titre '" + duplicatedTitle + "' existe déjà");
        }

        // Créer le nouveau board
        BoardEntity duplicatedBoard = BoardEntity.builder()
                .title(duplicatedTitle)
                .build();

        BoardEntity savedBoard = boardRepository.save(duplicatedBoard);

        // Dupliquer les colonnes (sans les tâches)
        if (originalBoard.getColumns() != null) {
            originalBoard.getColumns().forEach(originalColumn -> {
                ColumnEntity duplicatedColumn = ColumnEntity.builder()
                        .title(originalColumn.getTitle())
                        .board(savedBoard)
                        .build();
                columnRepository.save(duplicatedColumn);
            });
        }

        // Retourner le board dupliqué avec ses nouvelles colonnes
        return getById(savedBoard.getBoardId());
    }
}