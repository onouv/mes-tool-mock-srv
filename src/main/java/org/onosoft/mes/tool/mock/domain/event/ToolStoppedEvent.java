package org.onosoft.mes.tool.mock.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

public class ToolStoppedEvent extends ToolEvent {

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public ToolStoppedEvent(
    @JsonProperty("toolId") ToolId toolId) {
    super(toolId);
  }

  public String toString() {
    return String.format(
        "Tool id=%s STOPPED @ %s.", this.toolId, this.timeStamp);
  }
}
