package com.kaban.kabanplatform.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    /**
     * Trouve toutes les tâches d'une colonne spécifique
     */
    List<TaskEntity> findByColumnColumnId(UUID columnId);
}