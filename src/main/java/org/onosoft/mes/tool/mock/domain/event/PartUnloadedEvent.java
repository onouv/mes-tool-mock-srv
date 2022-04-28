package org.onosoft.mes.tool.mock.domain.event;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;

@Getter
public class PartUnloadedEvent extends PartEvent {
    public PartUnloadedEvent(Part part) {
        super(part);
    }

    public String toString() {
        return String.format(
            "Part %s unloaded at %s.", this.part.getId(), this.timeStamp);
    }
}
