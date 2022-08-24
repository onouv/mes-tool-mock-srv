package org.onosoft.mes.tool.mock.adapters.out.messaging;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;

import org.onosoft.mes.tool.mock.domain.required.DomainEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainEventPublisherDefault implements DomainEventPublisher {

  private static final Logger logger= LoggerFactory.getLogger(DomainEventPublisherDefault.class);

  public DomainEventPublisherDefault() {
  }

  @Override
  public boolean publish(List<DomainEvent> events) {
    logger.info("*********************");
    logger.info(String.format("POSTING DOMAIN EVENTS: %s", this.logEvents(events)));

    return true;
  }

  protected String logEvents(List<DomainEvent> events) {
    StringBuilder b = new StringBuilder();
    for (DomainEvent e : events) {
      b.append("[");
      b.append(e.toString());
      b.append("] ");
    }
    return b.toString();
  }
}
