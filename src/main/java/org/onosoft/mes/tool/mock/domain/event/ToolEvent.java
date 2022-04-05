package org.onosoft.mes.tool.mock.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public abstract class ToolEvent extends DomainEvent {
    protected final String toolId;
}
