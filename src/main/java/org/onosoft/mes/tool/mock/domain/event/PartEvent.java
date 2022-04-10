package org.onosoft.mes.tool.mock.domain.event;

import lombok.AllArgsConstructor;
import org.onosoft.mes.tool.mock.domain.provided.value.PartId;

@AllArgsConstructor
public abstract class PartEvent extends DomainEvent {
    protected PartId partId;
}
