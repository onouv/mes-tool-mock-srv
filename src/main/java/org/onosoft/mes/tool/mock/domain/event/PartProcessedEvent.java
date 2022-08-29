package org.onosoft.mes.tool.mock.domain.event;

import org.onosoft.mes.tool.mock.domain.provided.value.ProcessId;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;

public class PartProcessedEvent extends PartEvent {
  protected ProcessId processId;
  public PartProcessedEvent(Part part, ProcessId processId) {
    super(part);
    this.processId = processId;
  }

  @Override
  public String toString() {
    return String.format(
        "Part id = %s processed by process id = %s.",
        this.part.getId(),
        this.processId);
  }
}
