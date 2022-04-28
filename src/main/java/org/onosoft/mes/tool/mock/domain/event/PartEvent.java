package org.onosoft.mes.tool.mock.domain.event;

import lombok.AllArgsConstructor;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;

@AllArgsConstructor
public abstract class PartEvent extends DomainEvent {
    protected Part part;
}
