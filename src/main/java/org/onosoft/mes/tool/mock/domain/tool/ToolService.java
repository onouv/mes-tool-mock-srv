package org.onosoft.mes.tool.mock.domain.tool;

import org.onosoft.mes.tool.mock.adapters.out.messaging.DomainEventPublisherDefault;
import org.onosoft.ddd.annotations.DomainService;
import org.onosoft.mes.tool.mock.domain.exception.*;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolDefinition;
import org.onosoft.mes.tool.mock.domain.required.ToolRepository;
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

  public Tool setupNewTool(
      ToolId toolId,
      ToolDefinition definition) throws Exception {

    Tool candidate = ToolDefault.prototype(toolId, definition, this.publisher);
    if(this.toolRepository.findTool(toolId) != null)
      throw new ToolPreExistingException(toolId);

    this.toolRepository.insertTool(candidate);
    DomainResult result = candidate.create();
    this.postDomainEvents(result);
    return candidate;
  }

  public void destroyTool(ToolId toolId) throws NoSuchToolFoundException {
    Tool candidate = this.toolRepository.findTool(toolId);
    if(candidate == null)
      throw new NoSuchToolFoundException(toolId);

    DomainResult result = candidate.delete();
    this.toolRepository.removeTool(toolId);
    this.postDomainEvents(result);
  }

  public Tool start(ToolId toolId) throws NoSuchToolFoundException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.start();
    this.postDomainEvents(result);
    return tool;
  }

  public Tool stop(ToolId toolId) throws NoSuchToolFoundException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.stop();
    this.postDomainEvents(result);
    return tool;
  }

  public Tool loadPart(ToolId toolId, LoadportId portId, Part part)
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
    return tool;
  }

  public Tool unloadPart(ToolId toolId, LoadportId portId)
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
    return tool;
  }

  public Tool faultTool(ToolId toolId) throws  NoSuchToolFoundException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.fault();
    this.postDomainEvents(result);
    return tool;
  }

  public Tool clearFault(ToolId toolId) throws  NoSuchToolFoundException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.clearFault();
    this.postDomainEvents(result);
    return tool;
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

}
