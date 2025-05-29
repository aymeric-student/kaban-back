package com.kaban.kabanplatform.tasks;

import com.kaban.kabanplatform.column.ColumnEntity;
import com.kaban.kabanplatform.subtasks.SubTaskEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id", nullable = false)
    private ColumnEntity column;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SubTaskEntity> subTaskEntities = new ArrayList<>();

    private String title;

    private boolean status;
}
