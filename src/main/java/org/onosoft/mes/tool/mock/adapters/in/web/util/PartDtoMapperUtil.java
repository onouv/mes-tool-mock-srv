package org.onosoft.mes.tool.mock.adapters.in.web.util;

import org.onosoft.mes.tool.mock.adapters.in.web.parts.dto.PartDto;
import org.onosoft.mes.tool.mock.adapters.in.web.parts.dto.PartTypeDto;
import org.onosoft.mes.tool.mock.domain.provided.value.PartId;
import org.onosoft.mes.tool.mock.domain.provided.value.PartStatus;
import org.onosoft.mes.tool.mock.domain.provided.value.PartType;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;

public class PartDtoMapperUtil {
  public static PartDto map(Part part) {
    return PartDto.builder()
        .id(part.getId().toString())
        .type(PartDtoMapperUtil.map(part.getType()))
        .build();
  }

  public static Part map(PartDto dto) {
    return Part.builder()
        .id(new PartId(dto.getId()))
        .type(PartDtoMapperUtil.map(dto.getType()))
        .status(PartStatus.UNPROCESSED)
        .build();
  }

  public static PartTypeDto map(PartType type) {
    return PartTypeDto.builder()
        .id(type.getId())
        .description(type.getDescription())
        .parentId(type.getParentid())
        .build();
  }

  public static PartType map(PartTypeDto dto) {
    return PartType.builder()
        .id(dto.getId())
        .description(dto.getDescription())
        .parentid(dto.getParentId())
        .build();
  }
}
