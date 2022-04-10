package org.onosoft.mes.tool.mock.domain.event;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.PartId;

@Getter
public class PartUnloadedEvent extends PartEvent {
    public PartUnloadedEvent(PartId id) {
        super(id);
    }

    public String toString() {
        return String.format(
            "Part %s unloaded at %s.", this.partId, this.timeStamp);
    }
}
