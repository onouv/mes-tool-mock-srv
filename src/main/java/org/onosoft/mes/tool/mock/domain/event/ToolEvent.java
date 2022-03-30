package org.onosoft.mes.tool.mock.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.value.DomainEvent;

@AllArgsConstructor @Getter
public abstract class ToolEvent extends DomainEvent {
    protected final String toolId;
}
