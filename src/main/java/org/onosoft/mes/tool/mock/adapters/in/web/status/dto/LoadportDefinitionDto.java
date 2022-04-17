package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoadportDefinitionDto {
  String id;
  int capacity;
}
