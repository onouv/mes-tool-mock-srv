package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolStates;

import java.util.List;

@Getter
@SuperBuilder
public class ToolDescriptorDto {
  protected final String id;
  protected final String name;
  protected final String description;
  protected final List<ToolStates> states;
}
