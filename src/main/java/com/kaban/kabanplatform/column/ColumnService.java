package com.kaban.kabanplatform.column;

import com.kaban.kabanplatform.board.BoardEntity;
import com.kaban.kabanplatform.board.BoardRepository;
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
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public ColumnDto getById(UUID id) {
        ColumnEntity column = columnRepository.findByIdWithTasks(id)
                .orElseThrow(() -> new NotFoundException("Colonne avec l'ID " + id + " introuvable"));
        return ColumnMapper.toDtoWithTasks(column);
    }

    @Transactional(readOnly = true)
    public List<ColumnDto> getColumnsByBoard(UUID boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new NotFoundException("Board avec l'ID " + boardId + " introuvable");
        }
        return columnRepository.findByBoardBoardIdWithTasks(boardId).stream()
                .map(ColumnMapper::toDtoWithTasks)
                .toList();
    }

    public ColumnDto create(UUID boardId, ColumnDto columnDto) {
        // Validation du board
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("Board avec l'ID " + boardId + " introuvable"));

        // Validation du titre
        if (columnDto.getTitle() == null || columnDto.getTitle().isBlank()) {
            throw new BadRequestException("Le titre de la colonne ne peut pas être vide");
        }

        // Vérification de l'unicité du titre dans le board
        if (columnRepository.findByTitleAndBoardBoardId(columnDto.getTitle().trim(), boardId).isPresent()) {
            throw new BadRequestException("Une colonne avec ce titre existe déjà dans ce board");
        }

        ColumnEntity column = ColumnEntity.builder()
                .title(columnDto.getTitle().trim())
                .board(board)
                .build();

        ColumnEntity savedColumn = columnRepository.save(column);
        return ColumnMapper.toDtoWithoutTasks(savedColumn);
    }

    public ColumnDto update(UUID id, ColumnDto columnDto) {
        ColumnEntity column = columnRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Colonne avec l'ID " + id + " introuvable"));

        // Validation du titre
        if (columnDto.getTitle() == null || columnDto.getTitle().isBlank()) {
            throw new BadRequestException("Le titre de la colonne ne peut pas être vide");
        }

        // Vérification de l'unicité du titre dans le board (sauf pour la colonne actuelle)
        columnRepository.findByTitleAndBoardBoardId(columnDto.getTitle().trim(), column.getBoard().getBoardId())
                .ifPresent(existingColumn -> {
                    if (!existingColumn.getColumnId().equals(id)) {
                        throw new BadRequestException("Une colonne avec ce titre existe déjà dans ce board");
                    }
                });

        column.setTitle(columnDto.getTitle().trim());
        ColumnEntity updatedColumn = columnRepository.save(column);
        return ColumnMapper.toDtoWithoutTasks(updatedColumn);
    }

    public void delete(UUID id) {
        ColumnEntity column = columnRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Colonne avec l'ID " + id + " introuvable"));

        // Vérification que la colonne n'a pas de tâches (protection des données)
        if (column.getTasks() != null && !column.getTasks().isEmpty()) {
            throw new BadRequestException("Impossible de supprimer la colonne car elle contient des tâches. " +
                    "Veuillez d'abord déplacer ou supprimer toutes les tâches.");
        }

        columnRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countColumnsByBoard(UUID boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new NotFoundException("Board avec l'ID " + boardId + " introuvable");
        }
        return columnRepository.countByBoardBoardId(boardId);
    }
}