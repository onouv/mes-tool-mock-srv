package org.onosoft.mes.tool.mock.domain.required;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.event.PartInProcessEvent;
import org.onosoft.mes.tool.mock.domain.event.PartProcessedEvent;

import java.util.List;

public interface DomainEventPublisher {
  boolean publish(List<DomainEvent>events);
}
