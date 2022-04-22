package org.onosoft.mes.tool.mock.adapters.in.web.parts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.onosoft.mes.tool.mock.domain.provided.value.PartType;

@Getter
@Builder
@AllArgsConstructor
public class PartDto {
  protected final String id;
  protected final PartType type;
}
