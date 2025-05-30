package com.kaban.kabanplatform.column;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ColumnRepository extends JpaRepository<ColumnEntity, UUID> {

    /**
     * Trouve toutes les colonnes d'un board spécifique
     */
    List<ColumnEntity> findByBoardBoardId(UUID boardId);

    /**
     * Trouve une colonne avec ses tâches chargées (évite le problème N+1)
     */
    @Query("SELECT c FROM ColumnEntity c LEFT JOIN FETCH c.tasks WHERE c.columnId = :id")
    Optional<ColumnEntity> findByIdWithTasks(@Param("id") UUID id);

    /**
     * Trouve toutes les colonnes d'un board avec leurs tâches
     */
    @Query("SELECT DISTINCT c FROM ColumnEntity c LEFT JOIN FETCH c.tasks WHERE c.board.boardId = :boardId")
    List<ColumnEntity> findByBoardBoardIdWithTasks(@Param("boardId") UUID boardId);

    /**
     * Compte le nombre de colonnes dans un board
     */
    long countByBoardBoardId(UUID boardId);

    /**
     * Vérifie si une colonne existe dans un board spécifique
     */
    boolean existsByColumnIdAndBoardBoardId(UUID columnId, UUID boardId);

    /**
     * Trouve les colonnes par titre (pour éviter les doublons)
     */
    List<ColumnEntity> findByTitleContainingIgnoreCase(String title);

    /**
     * Trouve une colonne par titre exact dans un board spécifique
     */
    Optional<ColumnEntity> findByTitleAndBoardBoardId(String title, UUID boardId);
}