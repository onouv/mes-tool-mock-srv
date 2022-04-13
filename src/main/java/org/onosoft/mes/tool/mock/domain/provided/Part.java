package org.onosoft.mes.tool.mock.domain.provided;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.ddd.annotations.Entity;
import org.onosoft.mes.tool.mock.adapters.in.web.parts.dto.PartDto;
import org.onosoft.mes.tool.mock.domain.provided.value.PartId;
import org.onosoft.mes.tool.mock.domain.provided.value.PartStatus;
import org.onosoft.mes.tool.mock.domain.provided.value.PartType;

@Entity
@Getter
public abstract class Part extends PartDto {

    public Part(PartId id, PartType type) {
        super(id, type);
    }
    public abstract PartStatus processSuccessful();
    public abstract PartStatus processWithFailure();
}
