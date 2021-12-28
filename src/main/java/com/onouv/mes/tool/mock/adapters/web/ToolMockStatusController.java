package com.onouv.mes.tool.mock.adapters.web;

import com.onouv.mes.tool.mock.adapters.web.api.ToolUpEvent;
import com.onouv.mes.tool.mock.adapters.web.api.ToolDownEvent;

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
	
	@RequestMapping(
			value={"/mes/tool/{tool-id-string}/mock/status/up"},
			method=RequestMethod.PUT)
	public ResponseEntity<Void> startTool(
			@PathVariable("tool-id-string") String toolId,
			@RequestBody ToolUpEvent body) {
		
		logger.info(String.format(
				"mes-toolmock-srv: processing PUT /mes/tool/%s/mock/status/up with body %s",
				toolId, 
				body.toString()));
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id-string}/mock/status/down"},
			method=RequestMethod.PUT)
	public ResponseEntity<Void> stopTool(
			@PathVariable("tool-id-string") String toolId,
			@RequestBody ToolDownEvent body) {
		
		logger.info(String.format(
				"mes-toolmock-srv: processing PUT /mes/tool/%s/mock/status/down with body %s",
				toolId, 
				body.toString()));
		
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
}


