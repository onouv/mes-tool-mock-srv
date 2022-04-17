package org.onosoft.mes.tool.mock.domain.tool.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.PartId;
import org.onosoft.mes.tool.mock.domain.provided.value.PartStatus;

@Getter
@SuperBuilder
public class DefaultPart extends Part {

    @Override
    public PartStatus processSuccessful() {
        return PartStatus.PROCESSED_GOOD;
    }

    @Override
    public PartStatus processWithFailure() {
        return PartStatus.PROCESSED_BAD;
    }
}
