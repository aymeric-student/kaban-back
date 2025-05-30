package com.kaban.kabanplatform.board;

import com.kaban.kabanplatform.column.ColumnDto;
import com.kaban.kabanplatform.column.ColumnMapper;

import java.util.List;

public class BoardMapper {

    /**
     * Convertit une BoardEntity en BoardDto (version complète avec colonnes)
     */
    public static BoardDto toDto(BoardEntity board) {
        List<ColumnDto> columns = List.of(); // Par défaut, liste vide

        // Si les colonnes sont chargées, les mapper
        if (board.getColumns() != null) {
            columns = board.getColumns().stream()
                    .map(ColumnMapper::toDtoWithoutTasks) // Sans les tâches pour éviter le chargement lourd
                    .toList();
        }

        return BoardDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .columns(columns)
                .build();
    }

    /**
     * Convertit une BoardEntity en BoardDto (version légère sans colonnes)
     */
    public static BoardDto toDtoLight(BoardEntity board) {
        return BoardDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .columns(List.of()) // Liste vide pour version légère
                .build();
    }

    /**
     * Convertit une BoardEntity en BoardDto avec colonnes ET tâches (version complète)
     */
    public static BoardDto toDtoWithTasks(BoardEntity board) {
        List<ColumnDto> columns = List.of(); // Par défaut, liste vide

        // Si les colonnes sont chargées, les mapper avec leurs tâches
        if (board.getColumns() != null) {
            columns = board.getColumns().stream()
                    .map(ColumnMapper::toDtoWithTasks) // Avec les tâches
                    .toList();
        }

        return BoardDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .columns(columns)
                .build();
    }

    /**
     * Convertit un BoardDto en BoardEntity (pour création/mise à jour)
     */
    public static BoardEntity toEntity(BoardDto dto) {
        return BoardEntity.builder()
                .boardId(dto.getBoardId())
                .title(dto.getTitle())
                .build();
    }
}