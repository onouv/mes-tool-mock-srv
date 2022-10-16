package org.onosoft.mes.tool.mock.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.value.TimeInstant;

@org.onosoft.ddd.annotations.DomainEvent
public class ToolIdleEvent extends ToolEvent {

  @JsonProperty("reason")
  protected IdleReason reason;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public ToolIdleEvent(
      @JsonProperty("toolId") ToolId toolId,
      IdleReason reason) {
    super(toolId);
    this.reason = reason;
  }

  public String toString() {
    return String.format(
        "Tool id=%s IDLE for reason %s @ %s.", this.toolId, this.reason, this.timeStamp);
  }
}
