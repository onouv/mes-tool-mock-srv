package org.onosoft.mes.tool.mock.adapters.in.web.status;

import org.onosoft.mes.tool.mock.domain.exception.NoSuchToolFoundException;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.util.ToolDtoMapperUtil;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDefinitionDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
import org.onosoft.mes.tool.mock.adapters.in.web.service.ToolService;

import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.required.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ToolMockStatusController {
	
	private static final Logger logger=LoggerFactory.getLogger(ToolMockStatusController.class);

	@Autowired
	ToolService domainService;

	@Autowired
	ToolRepository repository;

	@RequestMapping(
			value={"/mes/tool/{tool-id}/mock"},
			method = RequestMethod.POST
	)
	public ResponseEntity<ToolDto> createTool(
			@PathVariable("tool-id") String toolId,
			@RequestBody ToolDefinitionDto body) throws Exception {

		logger.info(String.format(
				"processing POST /mes/tool/%s/mock with body %s",
				toolId,
				body.toString()));

		ToolDto response = this.domainService.setupNewTool(
				new ToolId(toolId), ToolDtoMapperUtil.map(body));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/mes/tool/{tool-id}/mock", method = RequestMethod.GET)
	public ResponseEntity<ToolDto> readTool(@PathVariable("tool-id") String toolId)
		throws NoSuchToolFoundException {
		ToolId id = new ToolId(toolId);
		Tool tool = this.repository.findTool(id);

		if(tool == null)
			throw new NoSuchToolFoundException(id);

		return new ResponseEntity<>(ToolDtoMapperUtil.map(tool), HttpStatus.OK);
	}

	@RequestMapping(
			value={"/mes/tool/{tool-id}/mock"},
			method = RequestMethod.DELETE
	)
	public ResponseEntity<Void> deleteTool(@PathVariable("tool-id") String toolId)
		throws NoSuchToolFoundException {
		logger.info(String.format("processing DELETE /mes/tool/%s/mock", toolId));

		this.domainService.destroyTool(new ToolId(toolId));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(
			value={"/mes/tool/{tool-id}/mock/status/start"},
			method=RequestMethod.PUT)
	public ResponseEntity<ToolDto> startTool(
			@PathVariable("tool-id") String toolId) throws NoSuchToolFoundException {
		
		logger.info(String.format(
				"processing PUT /mes/tool/%s/mock/status/start",
				toolId));

		ToolDto response = this.domainService.start(new ToolId(toolId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id}/mock/status/stop"},
			method=RequestMethod.PUT)
	public ResponseEntity<ToolDto> stopTool(
			@PathVariable("tool-id") String toolId) throws NoSuchToolFoundException {
		
		logger.info(String.format(
				"processing PUT /mes/tool/%s/mock/status/stop",
				toolId));

		ToolDto response = this.domainService.stop(new ToolId(toolId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id}/mock/status/fault"},
			method=RequestMethod.PUT)
	public ResponseEntity<ToolDto> faultTool(
			@PathVariable("tool-id") String toolId) throws  NoSuchToolFoundException {

		logger.info(String.format(
				"processing PUT /mes/tool/%s/mock/status/fault",
				toolId));

		ToolDto response = this.domainService.faultTool(new ToolId(toolId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id}/mock/status/fault"},
			method=RequestMethod.DELETE)
	public ResponseEntity<ToolDto> clearFaultTool(
			@PathVariable("tool-id") String toolId) throws  NoSuchToolFoundException {

		logger.info(String.format(
				"processing DELETE /mes/tool/%s/mock/status/fault",
				toolId));

		ToolDto response = this.domainService.clearFault(new ToolId(toolId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}


