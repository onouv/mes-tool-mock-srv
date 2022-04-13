package org.onosoft.mes.tool.mock.adapters.in.web.parts.dto;

import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;

@Getter
public class PartStatusResponse {
  protected final String toolId;
  protected final String toolState;
  protected final PartDto part;

  public PartStatusResponse(
      ToolId toolId,
      ToolStates toolState,
      Part part
  ) {
    this.toolId = toolId.toString();
    this.toolState = toolState.toString();
    this.part = part;
  }
}
