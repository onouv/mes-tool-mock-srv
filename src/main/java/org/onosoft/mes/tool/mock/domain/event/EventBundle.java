package org.onosoft.mes.tool.mock.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.tool.DefaultTool;
import org.onosoft.mes.tool.mock.domain.value.DomainEvent;

import java.util.List;

@AllArgsConstructor
@Getter
public class EventBundle {

    final DefaultTool instance;
    final List<DomainEvent> events;
}
