package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoadportDto {
  // TODO: add type (IN | OUT) to allow for checking in loading/unloading operations
  String type;
  String id;
  int capacity;
  List<String> partIds;
}
