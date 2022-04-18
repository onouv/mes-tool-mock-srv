package org.onosoft.mes.tool.mock.adapters.in.web.service;

import org.onosoft.ddd.annotations.DomainService;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolDto;
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
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@DomainService
public class ToolService  {

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

  public ToolDto stop(ToolId toolId, IdleReason reason) throws NoSuchElementException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.stop(reason);
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

  public ToolDto breakDown(ToolId toolId) {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.breakDown();
    this.postDomainEvents(result);
    return this.buildResponseDto(tool);
  }

  public ToolDto repair(ToolId toolId) {
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
    System.out.printf("Posting domain events... \n%s%n", domainResult.toString());
    // TODO: actually post domainResult.events to MesBus
  }

  protected ToolDto buildResponseDto(Tool tool) {
    ToolDto dto = ToolDto.builder()
        .id(tool.getId().toString())
        .name(tool.getName())
        .description(tool.getDescription())
        .status(tool.getStatus().toString())
        .build();

    return dto;
  }

}
