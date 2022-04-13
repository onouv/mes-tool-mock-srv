package org.onosoft.mes.tool.mock.adapters.in.web.status;

import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolStatusResponse;
import org.onosoft.mes.tool.mock.domain.ToolService;
import org.onosoft.mes.tool.mock.domain.event.ToolUpEvent;
import org.onosoft.mes.tool.mock.domain.event.ToolDownEvent;

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
			value={"/mes/tool/{tool-id-string}/mock/status/start"},
			method=RequestMethod.PUT)
	public ResponseEntity<ToolStatusResponse> startTool(
			@PathVariable("tool-id-string") String toolId,
			@RequestBody ToolUpEvent body) {
		
		logger.info(String.format(
				"mes-toolmock-srv: processing PUT /mes/tool/%s/mock/status/up with body %s",
				toolId,
				body.toString()));

		ToolStatusResponse response = this.domainService.start(new ToolId(toolId));
		return new ResponseEntity<ToolStatusResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id-string}/mock/status/stop"},
			method=RequestMethod.PUT)
	public ResponseEntity<Void> stopTool(
			@PathVariable("tool-id-string") String toolId,
			@RequestBody ToolDownEvent body) {
		
		logger.info(String.format(
				"mes-toolmock-srv: processing PUT /mes/tool/%s/mock/status/down with body %s",
				toolId, 
				body.toString()));
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}

