package org.onosoft.mes.tool.mock.domain.event;

import org.onosoft.mes.tool.mock.domain.provided.Part;

public class PartLoadedEvent extends PartEvent {

    public PartLoadedEvent(Part part) {
        super(part);
    }

    @Override
    public String toString() {
        return String.format(
            "Part %s loaded at %s.", this.part.getId(), this.timeStamp);
    }

}
