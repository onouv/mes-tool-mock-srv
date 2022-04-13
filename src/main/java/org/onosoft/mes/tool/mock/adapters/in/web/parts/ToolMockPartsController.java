package org.onosoft.mes.tool.mock.adapters.in.web.parts;

import org.onosoft.mes.tool.mock.adapters.in.web.parts.dto.PartDto;
import org.onosoft.mes.tool.mock.adapters.in.web.parts.dto.PartStatusResponse;
import org.onosoft.mes.tool.mock.domain.ToolService;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
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
  public ResponseEntity<PartStatusResponse> loadPart(
      @PathVariable("tool-id") String toolId,
      @PathVariable("port-id") String portId,
      @RequestBody PartDto partDto) {

  }

  @RequestMapping(
      value = "/mes/tool/{tool-id}/mock/parts/unload",
      method = RequestMethod.GET
  )
  ResponseEntity<PartStatusResponse> unloadPart(
      @PathVariable("tool-id") String toolId,
      @PathVariable("port-id") String portId
  ) {

  }
}
