package org.onosoft.mes.tool.mock.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;

import java.util.List;

@AllArgsConstructor
@Getter
public class DomainResult {
  protected final List<DomainEvent> events;
  protected final
}
