package org.onosoft.mes.tool.mock.adapters.in.web.parts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PartTypeDto {
  protected String id;
  protected String description;
  protected String parentId;
}
