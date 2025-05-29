package com.kaban.kabanplatform.subtasks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubTaskRepository extends JpaRepository<SubTaskEntity, UUID> {
}
