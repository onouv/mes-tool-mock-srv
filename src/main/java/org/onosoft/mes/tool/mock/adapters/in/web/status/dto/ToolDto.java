package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ToolDto extends ToolDescriptorDto {
  protected final LoadportDto inport;
  protected final LoadportDto outport;
  protected final List<String> partsInProcess;
}
