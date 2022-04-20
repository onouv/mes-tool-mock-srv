package org.onosoft.mes.tool.mock.adapters.in.web.service;

import org.onosoft.mes.tool.mock.domain.event.DomainEvent;
import org.onosoft.ddd.annotations.DomainService;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
import org.onosoft.mes.tool.mock.domain.provided.util.DtoMapperUtil;
import org.onosoft.mes.tool.mock.domain.exception.ToolPreExistingException;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolDefinition;
import org.onosoft.mes.tool.mock.domain.required.ToolRepository;
import org.onosoft.mes.tool.mock.domain.tool.DefaultTool;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@DomainService
public class ToolService  {

  private static final Logger logger= LoggerFactory.getLogger(ToolService.class);


  @Autowired
  protected ToolRepository toolRepository;

  public ToolDto setupNewTool(
      ToolId toolId,
      ToolDefinition definition) throws ToolPreExistingException, Exception {

    Tool candidate = DefaultTool.prototype(toolId, definition, this.toolRepository);
    DomainResult result = candidate.create();
    this.postDomainEvents(result);
    return this.buildResponseDto(candidate);
  }

  public ToolDto start(ToolId toolId) throws NoSuchElementException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.start();
    this.postDomainEvents(result);

    return this.buildResponseDto(tool);
  }

  public ToolDto stop(ToolId toolId) throws NoSuchElementException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.stop();
    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto loadPart(ToolId toolId, LoadportId portId, Part part) throws NoSuchElementException, LoadportFullException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.loadPart(part, portId);

    // expose application-level exceptions to our clients
    ApplicationException e = result.getApplicationException();
    if(e instanceof LoadportFullException)
      throw (LoadportFullException) e;

    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto unloadPart(ToolId toolId, LoadportId portId)
      throws NoSuchElementException, NoPartAvailableException {

    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.unloadPart(portId);

    // expose application-level exceptions to our clients
    Exception e = result.getApplicationException();
    if(e instanceof NoPartAvailableException)
      throw (NoPartAvailableException) e;

    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto faultTool(ToolId toolId) {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.breakDown();
    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto clearFault(ToolId toolId) {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.repair();
    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  protected Tool validateTool(ToolId toolId) throws NoSuchElementException {
    Tool tool = toolRepository.findTool(toolId);
    if(tool == null)
      throw new NoSuchElementException(String.format("no tool with identifier %s found", toolId));

    return tool;
  }

  protected void postDomainEvents(DomainResult domainResult) {
    logger.info(String.format("Posting domain events: %s",this.logEvents(domainResult.getEvents())));
    // TODO: actually post domainResult.events to MesBus
  }

  protected ToolDto buildResponseDto(Tool tool) {
    return DtoMapperUtil.map(tool);
  }

  protected String logEvents(List<DomainEvent> events) {
    StringBuilder b = new StringBuilder();
    for (org.onosoft.mes.tool.mock.domain.event.DomainEvent e : events) {
      b.append("[");
      b.append(e.toString());
      b.append("] ");
    }
    return b.toString();
  }
}
