package com.furansujin.demosprinhexagonalcucumber.domain.entities;

import com.furansujin.demosprinhexagonalcucumber.domain.util.StringUtils;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.FileType;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateProcessSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OriginalRessource   {


    private final UUID id;

    private UUID projectId;

    private UUID createByUserId;

    private final String text;

    private final String name;

    private final SourceType type;

    private final FileType fileType;

    private final LocalDateTime createDate;

    private StateProcessSource stateProcessSource;

    public OriginalRessource(UUID projectId, String text, String name, SourceType type, FileType fileType, UUID createByUserId) {
        this.id = UUID.randomUUID();
        this.projectId = projectId;
        this.text = StringUtils.cleanString(text);
        this.name = name;
        this.type = type;
        this.fileType = fileType;   // Initialiser le nouveau champ
        this.createDate = LocalDateTime.now();
        this.stateProcessSource = StateProcessSource.PENDING;
        this.createByUserId = createByUserId;
    }

}
