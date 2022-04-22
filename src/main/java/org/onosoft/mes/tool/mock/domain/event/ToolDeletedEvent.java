package org.onosoft.mes.tool.mock.domain.event;

import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

public class ToolDeletedEvent extends ToolEvent {
  public ToolDeletedEvent(ToolId id) {
    super(id);
  }

  @Override
  public String toString() {
    return String.format("Tool %s DELETED @ %s",
        this.toolId.toString(),
        super.timeStamp);
  }
}
