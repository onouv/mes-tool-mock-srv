package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.Builder;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class ToolDto {
  protected final String id;
  protected final String name;
  protected final String description;
  protected final List<ToolStates> states;
  protected final LoadportDto inport;
  protected final LoadportDto outport;
  protected final List<String> partsInProcess;
}
