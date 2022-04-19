package org.onosoft.mes.tool.mock.domain.provided.value;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoadportDefinition {
  LoadportId id;
  int capacity;
}
