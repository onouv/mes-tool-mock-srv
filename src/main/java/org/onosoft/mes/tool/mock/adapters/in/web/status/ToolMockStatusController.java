package org.onosoft.mes.tool.mock.adapters.in.web.status;

import org.onosoft.mes.tool.mock.domain.provided.util.DtoMapperUtil;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDefinitionDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
import org.onosoft.mes.tool.mock.adapters.in.web.service.ToolService;

import org.onosoft.mes.tool.mock.domain.exception.ToolPreExistingException;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
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

	@RequestMapping(
			value={"/mes/tool/{tool-id}/mock"},
			method = RequestMethod.POST
	)
	public ResponseEntity<ToolDto> createTool(
			@PathVariable("tool-id") String toolId,
			@RequestBody ToolDefinitionDto body) throws ToolPreExistingException, Exception {

		logger.info(String.format(
				"mes-toolmock-srv: processing POST /mes/tool/%s/mock with body %s",
				toolId,
				body.toString()));

		ToolDto response = this.domainService.setupNewTool(
				new ToolId(toolId), DtoMapperUtil.map(body));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(
			value={"/mes/tool/{tool-id}/mock/status/start"},
			method=RequestMethod.PUT)
	public ResponseEntity<ToolDto> startTool(
			@PathVariable("tool-id") String toolId) {
		
		logger.info(String.format(
				"mes-toolmock-srv: processing PUT /mes/tool/%s/mock/status/up with body %s",
				toolId));

		ToolDto response = this.domainService.start(new ToolId(toolId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id}/mock/status/stop"},
			method=RequestMethod.PUT)
	public ResponseEntity<ToolDto> stopTool(
			@PathVariable("tool-id") String toolId) {
		
		logger.info(String.format(
				"mes-toolmock-srv: processing PUT /mes/tool/%s/mock/status/stop",
				toolId));

		ToolDto response = this.domainService.stop(new ToolId(toolId));
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id}/mock/status/fault"},
			method=RequestMethod.PUT)
	public ResponseEntity<ToolDto> faultTool(
			@PathVariable("tool-id") String toolId) {

		logger.info(String.format(
				"mes-toolmock-srv: processing PUT /mes/tool/%s/mock/status/breakdown",
				toolId));

		ToolDto response = this.domainService.faultTool(new ToolId(toolId));
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id}/mock/status/fault"},
			method=RequestMethod.DELETE)
	public ResponseEntity<ToolDto> clearFaultTool(
			@PathVariable("tool-id") String toolId) {

		logger.info(String.format(
				"mes-toolmock-srv: processing PUT /mes/tool/%s/mock/status/breakdown",
				toolId));

		ToolDto response = this.domainService.clearFault(new ToolId(toolId));
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}


