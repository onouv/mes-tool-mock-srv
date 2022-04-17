package org.onosoft.mes.tool.mock.domain.event;

import lombok.Builder;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

public class ToolCreatedEvent extends ToolEvent {

  public ToolCreatedEvent(ToolId id) {
    super(id);
  }

  @Override
  public String toString() {
    return String.format("Tool id=%s created @ %s",
        this.toolId.toString(),
        super.timeStamp);
  }
}
