package org.onosoft.mes.tool.mock.adapters.in.web.status;

import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDescriptorDto;
import org.onosoft.mes.tool.mock.domain.exception.NoSuchToolFoundException;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.adapters.in.web.util.ToolDtoMapperUtil;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDefinitionDto;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
import org.onosoft.mes.tool.mock.domain.tool.ToolService;

import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.required.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

//TODO : Remove @CrossOrigin once a proxy solution is in place

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
	@CrossOrigin(origins={"http://localhost:3000"})
	public ResponseEntity<ToolDto> createTool(
			@PathVariable("tool-id") String toolId,
			@RequestBody ToolDefinitionDto body) throws Exception {

		logger.info(String.format(
				"processing POST /mes/tool/%s/mock with body %s",
				toolId,
				body.toString()));

		Tool tool = this.domainService.setupNewTool(
				new ToolId(toolId), ToolDtoMapperUtil.map(body));

		return new ResponseEntity<>(ToolDtoMapperUtil.map(tool), HttpStatus.OK);
	}
	@RequestMapping(value = "/mes/tool/{tool-id}/mock", method = RequestMethod.GET)
	@CrossOrigin(origins={"http://localhost:3000"})
	public ResponseEntity<ToolDto> readTool(@PathVariable("tool-id") String toolId)
		throws NoSuchToolFoundException {
		ToolId id = new ToolId(toolId);
		Tool tool = this.repository.findTool(id);

		if(tool == null)
			throw new NoSuchToolFoundException(id);

		return new ResponseEntity<>(ToolDtoMapperUtil.map(tool), HttpStatus.OK);
	}

	@RequestMapping(value={"/mes/tool/mock/overviews"}, method=RequestMethod.GET)
	@CrossOrigin(origins={"http://localhost:3000"})
	public ResponseEntity<List<ToolDescriptorDto>> readOverview() {
		logger.info("processing GET /mes/tool/mock/overviews");

		List<Tool> tools = this.repository.findAll();
		List<ToolDescriptorDto> dtos = new ArrayList<>();
		//tools.stream().map(t -> dtos.add(ToolDtoMapperUtil.from(t)));

		for(Tool t: tools) {
			dtos.add(ToolDtoMapperUtil.from(t));
		}

		logger.info(String.format("serving GET /mes/tool/mock/overviews with %d items", dtos.size()));
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}


	@RequestMapping(
			value={"/mes/tool/{tool-id}/mock"},
			method = RequestMethod.DELETE
	)
	@CrossOrigin(origins={"http://localhost:3000"})
	public ResponseEntity<Void> deleteTool(@PathVariable("tool-id") String toolId)
		throws NoSuchToolFoundException {
		logger.info(String.format("processing DELETE /mes/tool/%s/mock", toolId));

		this.domainService.destroyTool(new ToolId(toolId));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(
			value={"/mes/tool/{tool-id}/mock/status/start"},
			method=RequestMethod.PUT)
	@CrossOrigin(origins={"http://localhost:3000"})
	public ResponseEntity<ToolDto> startTool(
			@PathVariable("tool-id") String toolId) throws NoSuchToolFoundException {
		
		logger.info(String.format(
				"processing PUT /mes/tool/%s/mock/status/start",
				toolId));

		Tool tool = this.domainService.start(new ToolId(toolId));
		return new ResponseEntity<>(ToolDtoMapperUtil.map(tool), HttpStatus.OK);
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id}/mock/status/stop"},
			method=RequestMethod.PUT)
	@CrossOrigin(origins={"http://localhost:3000"})
	public ResponseEntity<ToolDto> stopTool(
			@PathVariable("tool-id") String toolId) throws NoSuchToolFoundException {
		
		logger.info(String.format(
				"processing PUT /mes/tool/%s/mock/status/stop",
				toolId));

		Tool tool = this.domainService.stop(new ToolId(toolId));
		return new ResponseEntity<>(ToolDtoMapperUtil.map(tool), HttpStatus.OK);
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id}/mock/status/fault"},
			method=RequestMethod.PUT)
	@CrossOrigin(origins={"http://localhost:3000"})
	public ResponseEntity<ToolDto> faultTool(
			@PathVariable("tool-id") String toolId) throws  NoSuchToolFoundException {

		logger.info(String.format(
				"processing PUT /mes/tool/%s/mock/status/fault",
				toolId));

		Tool tool = this.domainService.faultTool(new ToolId(toolId));
		return new ResponseEntity<>(ToolDtoMapperUtil.map(tool), HttpStatus.OK);
	}

	@RequestMapping(
			value= {"/mes/tool/{tool-id}/mock/status/fault"},
			method=RequestMethod.DELETE)
	@CrossOrigin(origins={"http://localhost:3000"})
	public ResponseEntity<ToolDto> clearFaultTool(
			@PathVariable("tool-id") String toolId) throws  NoSuchToolFoundException {

		logger.info(String.format(
				"processing DELETE /mes/tool/%s/mock/status/fault",
				toolId));

		Tool tool = this.domainService.clearFault(new ToolId(toolId));
		return new ResponseEntity<>(ToolDtoMapperUtil.map(tool), HttpStatus.OK);
	}
}


