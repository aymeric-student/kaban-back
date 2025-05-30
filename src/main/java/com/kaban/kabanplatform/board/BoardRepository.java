package com.kaban.kabanplatform.board;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<BoardEntity, UUID> {

    /**
     * Trouve un board avec ses colonnes chargées (évite le problème N+1)
     */
    @Query("SELECT b FROM BoardEntity b LEFT JOIN FETCH b.columns WHERE b.boardId = :id")
    Optional<BoardEntity> findByIdWithColumns(@Param("id") UUID id);

    /**
     * Trouve tous les boards avec leurs colonnes
     */
    @Query("SELECT DISTINCT b FROM BoardEntity b LEFT JOIN FETCH b.columns")
    List<BoardEntity> findAllWithColumns();

    /**
     * Trouve les boards par titre
     */
    List<BoardEntity> findByTitleContainingIgnoreCase(String title);

    /**
     * Vérifie si un board existe par titre
     */
    boolean existsByTitle(String title);
}
