package org.onosoft.mes.tool.mock.adapters.in.web.parts;

import org.onosoft.mes.tool.mock.adapters.in.web.parts.dto.PartDto;
import org.onosoft.mes.tool.mock.adapters.in.web.service.ToolService;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ToolMockPartsController {

  @Autowired
  ToolService domainService;

  @RequestMapping(
      value = {"/mes/tool/{tool-id}/mock/parts/load/{port-id}/{part}"},
      method = {RequestMethod.POST}
  )
  public ResponseEntity<ToolDto> loadPart(
      @PathVariable("tool-id") String toolId,
      @PathVariable("port-id") String portId,
      @RequestBody PartDto partDto) {

    return null; // TODO

  }

  @RequestMapping(
      value = "/mes/tool/{tool-id}/mock/parts/unload",
      method = RequestMethod.GET
  )
  ResponseEntity<ToolDto> unloadPart(
      @PathVariable("tool-id") String toolId,
      @PathVariable("port-id") String portId) {

    return null; // TODO
  }
}
