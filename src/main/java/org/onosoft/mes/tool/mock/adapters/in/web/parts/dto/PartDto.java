package org.onosoft.mes.tool.mock.adapters.in.web.parts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PartDto {
  protected final String id;
  protected final PartTypeDto type;
}
