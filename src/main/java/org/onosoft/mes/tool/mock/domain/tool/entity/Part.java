package org.onosoft.mes.tool.mock.domain.tool.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.onosoft.ddd.annotations.Entity;
import org.onosoft.mes.tool.mock.domain.provided.value.PartId;
import org.onosoft.mes.tool.mock.domain.provided.value.PartStatus;
import org.onosoft.mes.tool.mock.domain.provided.value.PartType;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Part {

    protected PartId id;
    protected PartType type;
    protected PartStatus status;

    public Part(PartId id, PartType type) {
        this.id = id;
        this.type = type;
        this.status = PartStatus.UNPROCESSED;
    }
    public void processSuccessful() {
        this.status = PartStatus.PROCESSED_GOOD;
    };

    public void processWithFailure() {
        this.status = PartStatus.PROCESSED_BAD;
    };
}
