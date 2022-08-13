package org.onosoft.mes.tool.mock.domain.required;

import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;

import java.util.List;

public interface ToolRepository {
  boolean insertTool(Tool tool)  throws IllegalArgumentException;
  Tool findTool(ToolId id);
  boolean removeTool(ToolId id);

  List<Tool> findAll();
}
