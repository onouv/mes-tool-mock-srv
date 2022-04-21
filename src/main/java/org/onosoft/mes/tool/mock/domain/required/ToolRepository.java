package org.onosoft.mes.tool.mock.domain.required;

import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

public interface ToolRepository {
  boolean insertTool(Tool tool)  throws IllegalArgumentException;
  Tool findTool(ToolId id);
  boolean removeTool(ToolId id);
}
