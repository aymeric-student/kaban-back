package com.kaban.kabanplatform.board;

import com.kaban.kabanplatform.column.ColumnDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private UUID boardId;
    private String title;
    private List<ColumnDto> columns;
}
