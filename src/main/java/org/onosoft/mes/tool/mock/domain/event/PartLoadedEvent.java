package org.onosoft.mes.tool.mock.domain.event;

import org.onosoft.mes.tool.mock.domain.provided.value.PartId;

public class PartLoadedEvent extends PartEvent {

    public PartLoadedEvent(PartId partId) {
        super(partId);
    }

    @Override
    public String toString() {
        return String.format(
            "Part %s loaded at %s.", this.partId, this.timeStamp);
    }

}
