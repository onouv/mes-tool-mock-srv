package org.onosoft.mes.tool.mock.domain.provided.value;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ToolDefinition {
  protected final String name;
  protected final String description;
  protected final LoadportDefinition inport;
  protected final LoadportDefinition outport;
}
