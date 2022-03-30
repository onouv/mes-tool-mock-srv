package org.onosoft.mes.tool.mock.domain.event;

import lombok.Getter;

@Getter
public class PartUnloadedEvent extends PartEvent {
    public PartUnloadedEvent(String id) {
        super(id);
    }
}
