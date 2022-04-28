package org.onosoft.mes.tool.mock.domain.event;

import org.onosoft.mes.tool.mock.domain.provided.value.ProcessId;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;

public class PartInProcessEvent extends PartEvent {
  protected ProcessId processId;

  public PartInProcessEvent(Part part, ProcessId processId) {
    super(part);
    this.processId = processId;
  }

  @Override
  public String toString() {
    return String.format(
        "Part id = %s entering process id = %s @ %s.",
        this.part.getId(),
        this.processId,
        this.timeStamp);
  }
}
