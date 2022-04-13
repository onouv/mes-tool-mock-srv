package org.onosoft.mes.tool.mock.domain;

import org.onosoft.ddd.annotations.DomainService;
import org.onosoft.mes.tool.mock.domain.event.EventBundle;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.Identifier;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.DefaultTool;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@DomainService
public class ToolService  {

  // TODO: make fixed set of tools changeable (API + persistency)
  private final DefaultTool tool1 = new DefaultTool(new ToolId(":100.001"));
  private final DefaultTool tool2 = new DefaultTool(new ToolId(":100.002"));

  public void start(ToolId toolId) throws NoSuchElementException {
    Tool tool = this.validateTool(toolId);
    EventBundle result = tool.start();
    System.out.println(String.format("Tool.start produced domain result: ", result));
  }

  public void stop(ToolId toolId, IdleReason reason) throws NoSuchElementException {
    Tool tool = this.validateTool(toolId);
    EventBundle result = tool.stop(reason);
    System.out.println(String.format("Tool.stop produced domain result: ", result));
  }

  public void loadPart(ToolId toolId, Part part) throws NoSuchElementException, LoadportFullException {
    Tool tool = this.validateTool(toolId);
    EventBundle result = tool.loadPart(part);

    // expose appplication-level exceptions to our clients
    ApplicationException e = result.getApplicationException();
    if(e instanceof LoadportFullException)
      throw (LoadportFullException) e;

    System.out.println(String.format("Tool.loadPart produced domain result: ", result));

    // TODO : post domain events
  }

  public void unloadPart(ToolId toolId, Identifier port)
      throws NoSuchElementException, NoPartAvailableException {

    Tool tool = this.validateTool(toolId);
    EventBundle result = tool.unloadPart();

    // expose appplication-level exceptions to our clients
    Exception e = result.getApplicationException();
    if(e instanceof NoPartAvailableException)
      throw (NoPartAvailableException) e;

    System.out.println(String.format("Tool.unloadPart produced domain result: ", result));
  }

  public void breakDown(ToolId toolId) {
    Tool tool = this.validateTool(toolId);
    EventBundle result = tool.breakDown();
    System.out.println(String.format("Tool.breakdown produced domain result: ", result));
  }

  public void repair(ToolId toolId) {
    Tool tool = this.validateTool(toolId);
    EventBundle result = tool.repair();
    System.out.println(String.format("Tool.repair produced domain result: ", result));
  }

  protected Tool validateTool(ToolId toolId) throws NoSuchElementException {
    if(toolId.equals(this.tool1.getId()))
      return tool1;

    if (toolId.equals(this.tool2.getId()))
      return tool2;

    throw new NoSuchElementException(String.format("no tool with identifier %s found", toolId));
  }

  protected void rethrowAnyApplicationException(EventBundle result)
      throws NoPartAvailableException, LoadportFullException {

    Exception e = result.getApplicationException();
    if(e instanceof NoPartAvailableException)
      throw (NoPartAvailableException) e;

    if(e instanceof LoadportFullException)
      throw (LoadportFullException) e;
  }
}
