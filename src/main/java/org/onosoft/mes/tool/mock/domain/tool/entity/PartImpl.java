package org.onosoft.mes.tool.mock.domain.tool.entity;

import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.tool.value.PartStatus;

public class PartImpl extends Part {

    public PartImpl(String id) {
        super(id, PartStatus.UNPROCESSED);
    }

    public PartImpl(String id, PartStatus status) {
        super(id, status);
    }

    @Override
    public PartStatus processSuccessful() {
        return PartStatus.PROCESSED_GOOD;
    }

    @Override
    public PartStatus processWithFailure() {
        return PartStatus.PROCESSED_BAD;
    }
}
