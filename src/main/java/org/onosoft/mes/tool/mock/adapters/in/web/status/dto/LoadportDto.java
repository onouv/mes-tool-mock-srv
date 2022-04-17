package org.onosoft.mes.tool.mock.adapters.in.web.status.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoadportDto {
  String id;
  int capacity;
  List<String> partIds;
}
