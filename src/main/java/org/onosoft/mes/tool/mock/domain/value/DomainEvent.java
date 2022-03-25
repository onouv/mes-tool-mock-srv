package org.onosoft.mes.tool.mock.domain.value;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DomainEvent {
    protected final TimeInstant timeStamp;

    protected DomainEvent() {
        this.timeStamp = new TimeInstant();
    }
}
