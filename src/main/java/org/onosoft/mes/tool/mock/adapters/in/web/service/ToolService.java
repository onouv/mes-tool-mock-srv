package org.onosoft.mes.tool.mock.adapters.in.web.service;

import org.onosoft.mes.tool.mock.adapters.out.messaging.DomainEventPublisherDefault;
import org.onosoft.ddd.annotations.DomainService;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
import org.onosoft.mes.tool.mock.domain.exception.*;
import org.onosoft.mes.tool.mock.domain.provided.util.ToolDtoMapperUtil;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolDefinition;
import org.onosoft.mes.tool.mock.domain.required.ToolRepository;
import org.onosoft.mes.tool.mock.domain.tool.DefaultTool;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;
import org.onosoft.mes.tool.mock.domain.tool.entity.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DomainService
public class ToolService  {

  private static final Logger logger= LoggerFactory.getLogger(ToolService.class);


  @Autowired
  protected ToolRepository toolRepository;

  @Autowired
  protected DomainEventPublisherDefault publisher;

  public ToolDto setupNewTool(
      ToolId toolId,
      ToolDefinition definition) throws Exception {

    Tool candidate = DefaultTool.prototype(toolId, definition, this.toolRepository, this.publisher);
    DomainResult result = candidate.create();
    this.postDomainEvents(result);
    return this.buildResponseDto(candidate);
  }

  public void destroyTool(ToolId toolId) throws NoSuchToolFoundException {
    Tool candidate = this.toolRepository.findTool(toolId);
    if(candidate == null)
      throw new NoSuchToolFoundException(toolId);

    DomainResult result = candidate.delete();
    this.toolRepository.removeTool(toolId);
    this.postDomainEvents(result);
  }

  public ToolDto start(ToolId toolId) throws NoSuchToolFoundException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.start();
    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto stop(ToolId toolId) throws NoSuchToolFoundException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.stop();
    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto loadPart(ToolId toolId, LoadportId portId, Part part)
      throws  NoSuchToolFoundException,
              IllegalLoadportTypeException,
              LoadportFullException {

    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.loadPart(part, portId);

    // expose application-level exceptions from transition
    ApplicationException e = result.getApplicationException();
    if(e instanceof LoadportFullException)
      throw (LoadportFullException) e;

    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto unloadPart(ToolId toolId, LoadportId portId)
      throws  NoSuchToolFoundException,
              IllegalLoadportTypeException,
              NoPartAvailableException {

    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.unloadPart(portId);

    // expose application-level exceptions from transition
    Exception e = result.getApplicationException();
    if(e instanceof NoPartAvailableException)
      throw (NoPartAvailableException) e;

    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto faultTool(ToolId toolId) throws  NoSuchToolFoundException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.fault();
    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto clearFault(ToolId toolId) throws  NoSuchToolFoundException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.clearFault();
    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  protected Tool validateTool(ToolId toolId) throws NoSuchToolFoundException {
    Tool tool = toolRepository.findTool(toolId);
    if(tool == null)
      throw new NoSuchToolFoundException(toolId);

    return tool;
  }

  protected void postDomainEvents(DomainResult domainResult) {
    this.publisher.publish(domainResult.getEvents());
  }

  protected ToolDto buildResponseDto(Tool tool) {
    return ToolDtoMapperUtil.map(tool);
  }
}
