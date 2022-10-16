package org.onosoft.mes.tool.mock.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

public class ToolStartedEvent extends  ToolEvent{
  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public ToolStartedEvent(
      @JsonProperty("toolId") ToolId toolId) {
    super(toolId);
  }

  public String toString() {
    return String.format(
        "Tool id=%s STARTED @ %s.", this.toolId, this.timeStamp);
  }
}
