package org.onosoft.mes.tool.mock.adapters.in.web.service;

import org.onosoft.mes.tool.mock.adapters.in.web.parts.dto.PartDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.LoadportDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.LoadportDefinitionDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDefinitionDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportDefinition;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolDefinition;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DtoMapper {
  public static ToolDto map(Tool tool) {
    ToolDto dto = ToolDto.builder()
        .id(tool.getId().toString())
        .name(tool.getName())
        .description(tool.getDescription())
        .status(tool.getStatus().toString())
        .build();

    return dto;
  }

  public static LoadportDto map(LoadPort port) {
    List<String> partIds = new ArrayList<>();
    Iterator<Part> iter = port.getParts().iterator();
    while(iter.hasNext()) {
      partIds.add(iter.next().getId().toString());
    }

    LoadportDto dto = LoadportDto.builder()
        .capacity(port.capacity())
        .id(port.getId().toString())
        .partIds(partIds)
        .build();

    return dto;
  }

  public static LoadportDefinitionDto map(LoadportDefinition definition) {
    return LoadportDefinitionDto.builder()
        .id(definition.getId().toString())
        .capacity(definition.getCapacity())
        .build();
  }

  public static LoadportDefinition map(LoadportDefinitionDto dto) {
    return LoadportDefinition.builder()
        .id(new LoadportId(dto.getId()))
        .capacity(dto.getCapacity())
        .build();
  }

  public static ToolDefinition map(ToolDefinitionDto dto) {
    return ToolDefinition.builder()
        .name(dto.getName())
        .description(dto.getDescription())
        .inport(DtoMapper.map(dto.getInport()))
        .outport(DtoMapper.map(dto.getOutport()))
        .build();
  }

  public static PartDto map(Part part) {
    return PartDto.builder()
        .id(part.getId())
        .type(part.getType())
        .build();
  }

}
