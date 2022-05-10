package org.onosoft.mes.tool.mock.adapters.out.persist;

import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.required.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToolRepositoryDefault implements ToolRepository {
  protected List<Tool> tools = new ArrayList<>();

  @Override
  public boolean insertTool(Tool tool)  throws IllegalArgumentException {
    if(findTool(tool.getId()) != null)
      throw new IllegalArgumentException(
          String.format("Tool with id=%s already existing.", tool.getId().toString()));

    return tools.add(tool);
  }
  @Override
  public Tool findTool(ToolId id) {
    return tools.stream()
        .filter(t -> id.equals(t.getId()))
        .findAny()
        .orElse(null);
  }

  @Override
  public List<Tool> findAll() {
    return this.tools;
  }

  @Override
  public boolean removeTool(ToolId id) {
    return tools.removeIf(t -> id.equals(t.getId()));
  }
}
