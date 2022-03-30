package org.onosoft.mes.tool.mock.domain.event;

import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.tool.ToolImpl;
import org.onosoft.mes.tool.mock.domain.value.DomainEvent;

import java.util.List;

public class PartBundle extends EventBundle {
    public PartBundle(ToolImpl instance, Part part, List<DomainEvent> events) {
        super(instance, events);
        this.part = part;
    }

    public final Part part;
}