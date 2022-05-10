package org.onosoft.mes.tool.mock.adapters.in.web.util;

import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.*;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportDefinition;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.PartId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolDefinition;
import org.onosoft.mes.tool.mock.domain.tool.entity.LoadPort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToolDtoMapperUtil {

  protected static List<String> map(List<PartId> partIds) {
    List<String> result = new ArrayList<>();
    for (PartId partId : partIds) {
      result.add(partId.toString());
    }
    return result;
  }

  public static ToolDescriptorDto from(Tool tool) {
    return ToolDescriptorDto.builder()
        .id(tool.getId().toString())
        .name(tool.getName())
        .description(tool.getDescription())
        .states(tool.getCurrentStates())
        .build();
  }


  public static ToolDto map(Tool tool) {
    return ToolDto.builder()
        .id(tool.getId().toString())
        .name(tool.getName())
        .description(tool.getDescription())
        .states(tool.getCurrentStates())
        .inport(ToolDtoMapperUtil.map(tool.getInport()))
        .outport(ToolDtoMapperUtil.map(tool.getOutport()))
        .partsInProcess(map(tool.getPartsInProcess()))
        .build();
  }

  public static LoadportDto map(LoadPort port) {
    List<String> partIds = new ArrayList<>();
    for (Part part : port.getParts()) {
      partIds.add(part.getId().toString());
    }

    return LoadportDto.builder()
        .capacity(port.capacity())
        .id(port.getId().toString())
        .partIds(partIds)
        .type(port.getType().toString())
        .build();
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
        .inport(ToolDtoMapperUtil.map(dto.getInport()))
        .outport(ToolDtoMapperUtil.map(dto.getOutport()))
        .build();
  }



}
