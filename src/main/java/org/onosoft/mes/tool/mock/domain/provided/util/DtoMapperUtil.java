package org.onosoft.mes.tool.mock.domain.provided.util;

import org.onosoft.mes.tool.mock.adapters.in.web.parts.dto.PartDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.LoadportDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.LoadportDefinitionDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDefinitionDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
import org.onosoft.mes.tool.mock.domain.provided.Part;
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
public class DtoMapperUtil {

  protected static List<String> map(List<PartId> partIds) {
    List<String> result = new ArrayList<>();
    for (PartId partId : partIds) {
      result.add(partId.toString());
    }
    return result;
  }

  public static ToolDto map(Tool tool) {
    return ToolDto.builder()
        .id(tool.getId().toString())
        .name(tool.getName())
        .description(tool.getDescription())
        .states(tool.getCurrentStates())
        .inport(DtoMapperUtil.map(tool.getInport()))
        .outport(DtoMapperUtil.map(tool.getOutport()))
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
        .inport(DtoMapperUtil.map(dto.getInport()))
        .outport(DtoMapperUtil.map(dto.getOutport()))
        .build();
  }

  public static PartDto map(Part part) {
    return PartDto.builder()
        .id(part.getId())
        .type(part.getType())
        .build();
  }

}
