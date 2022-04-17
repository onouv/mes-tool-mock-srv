package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ToolDto {
  protected final String id;
  protected final String name;
  protected final String description;
  protected final String status;
  protected final LoadportDto inportId;
  protected final LoadportDto outportId;
  protected final List<String> partsInProcess;
}
