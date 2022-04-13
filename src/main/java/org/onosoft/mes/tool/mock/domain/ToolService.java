package org.onosoft.mes.tool.mock.domain;

import org.onosoft.ddd.annotations.DomainService;
import org.onosoft.mes.tool.mock.adapters.in.web.status.dto.ToolStatusResponse;
import org.onosoft.mes.tool.mock.domain.value.DomainResult;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.LoadportId;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.DefaultTool;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@DomainService
public class ToolService  {

  // TODO: make fixed set of tools changeable (API + persistence)
  private final DefaultTool tool1 = new DefaultTool(new ToolId(":100.001"));
  private final DefaultTool tool2 = new DefaultTool(new ToolId(":100.002"));

  public ToolStatusResponse start(ToolId toolId) throws NoSuchElementException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.start();
    this.postDomainEvents(result);

    return new ToolStatusResponse(toolId, result.getToolState());
  }

  public ToolStatusResponse stop(ToolId toolId, IdleReason reason) throws NoSuchElementException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.stop(reason);
    this.postDomainEvents(result);
    return new ToolStatusResponse(toolId, result.getToolState());
  }

  public void loadPart(ToolId toolId, LoadportId portId, Part part) throws NoSuchElementException, LoadportFullException {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.loadPart(part, portId);

    // expose application-level exceptions to our clients
    ApplicationException e = result.getApplicationException();
    if(e instanceof LoadportFullException)
      throw (LoadportFullException) e;

    this.postDomainEvents(result);
  }

  public void unloadPart(ToolId toolId, LoadportId portId)
      throws NoSuchElementException, NoPartAvailableException {

    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.unloadPart(portId);

    // expose application-level exceptions to our clients
    Exception e = result.getApplicationException();
    if(e instanceof NoPartAvailableException)
      throw (NoPartAvailableException) e;

    this.postDomainEvents(result);
  }

  public ToolStatusResponse breakDown(ToolId toolId) {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.breakDown();
    this.postDomainEvents(result);
    return new ToolStatusResponse(toolId, result.getToolState());
  }

  public ToolStatusResponse repair(ToolId toolId) {
    Tool tool = this.validateTool(toolId);
    DomainResult result = tool.repair();
    this.postDomainEvents(result);
    return new ToolStatusResponse(toolId, result.getToolState());
  }

  protected Tool validateTool(ToolId toolId) throws NoSuchElementException {
    if(toolId.equals(this.tool1.getId()))
      return tool1;

    if (toolId.equals(this.tool2.getId()))
      return tool2;

    throw new NoSuchElementException(String.format("no tool with identifier %s found", toolId));
  }

  protected void postDomainEvents(DomainResult domainResult) {
    System.out.printf("Posting domain events... \n%s%n", domainResult.toString());
    // TODO: actually post domainResult.events to MesBus
  }
}
