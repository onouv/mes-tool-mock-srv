package org.onosoft.mes.tool.mock.adapters.in.web.parts;

import org.onosoft.mes.tool.mock.adapters.in.web.parts.dto.PartDto;
import org.onosoft.mes.tool.mock.adapters.in.web.service.ToolService;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
import org.onosoft.mes.tool.mock.domain.exception.IllegalLoadportTypeException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.exception.NoSuchToolFoundException;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.provided.util.DtoMapperUtil;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ToolMockPartsController {

  private static final Logger logger= LoggerFactory.getLogger(ToolMockPartsController.class);

  @Autowired
  ToolService domainService;

  @RequestMapping(
      value = {"/mes/tool/{tool-id}/mock/parts/load/{port-id}"},
      method = {RequestMethod.POST}
  )
  public ResponseEntity<ToolDto> loadPart(
      @PathVariable("tool-id") String toolId,
      @PathVariable("port-id") String portId,
      @RequestBody PartDto partDto)

      throws  NoSuchToolFoundException,
              LoadportFullException,
              IllegalLoadportTypeException {

    logger.info(String.format(
        "processing POST /mes/tool/%s/mock/parts/load/%s",
        toolId, portId));

    Part part = DtoMapperUtil.map(partDto);
    ToolDto response = this.domainService.loadPart(
        new ToolId(toolId),
        new LoadportId(portId),
        part);

    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @RequestMapping(
      value = "/mes/tool/{tool-id}/mock/parts/unload/{port-d}",
      method = RequestMethod.GET
  )
  ResponseEntity<ToolDto> unloadPart(
      @PathVariable("tool-id") String toolId,
      @PathVariable("port-id") String portId)
      throws  NoSuchToolFoundException,
              IllegalLoadportTypeException,
              NoPartAvailableException  {

    logger.info(String.format(
        "processing GET /mes/tool/%s/mock/parts/unload/%s",
        toolId, portId));

    ToolDto response = this.domainService.unloadPart(
        new ToolId(toolId),
        new LoadportId(portId));

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
