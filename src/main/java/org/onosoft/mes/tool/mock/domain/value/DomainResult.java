package org.onosoft.mes.tool.mock.domain.value;

import lombok.Builder;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import java.util.List;

@Getter
@Builder
public class DomainResult {
  protected final Tool tool;
  protected final List<DomainEvent> events;
  protected final ApplicationException applicationException;
}
