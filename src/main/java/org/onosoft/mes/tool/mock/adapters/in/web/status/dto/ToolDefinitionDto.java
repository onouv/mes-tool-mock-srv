package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;

@Getter
@Builder
@ToString
public class ToolDefinitionDto {
  protected final String name;
  protected final String description;
  protected final LoadportDefinitionDto inport;
  protected final LoadportDefinitionDto outport;
}
