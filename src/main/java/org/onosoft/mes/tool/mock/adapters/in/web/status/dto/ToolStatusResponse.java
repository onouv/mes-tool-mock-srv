package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;


@Getter
@AllArgsConstructor
public class ToolStatusResponse {
  final String toolId;
  final protected String resultingState;

  public ToolStatusResponse(ToolId toolId, ToolStates resultingState) {
    this(toolId.toString(), resultingState.toString());
  }
}
