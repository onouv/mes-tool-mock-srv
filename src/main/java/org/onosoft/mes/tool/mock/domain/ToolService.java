package org.onosoft.mes.tool.mock.domain;

import org.onosoft.ddd.annotations.DomainService;
import org.onosoft.mes.tool.mock.domain.event.EventBundle;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.onosoft.mes.tool.mock.domain.exception.NoPartAvailableException;
import org.onosoft.mes.tool.mock.domain.provided.Part;
import org.onosoft.mes.tool.mock.domain.provided.Tool;
import org.onosoft.mes.tool.mock.domain.provided.value.Identifier;
import org.onosoft.mes.tool.mock.domain.provided.value.IdleReason;
import org.onosoft.mes.tool.mock.domain.provided.value.ToolId;
import org.onosoft.mes.tool.mock.domain.tool.DefaultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@DomainService
public class ToolService  {

  private final DefaultTool tool = new DefaultTool(new ToolId(":100.001"));

  public void start(Identifier toolId) throws NoSuchElementException {
    this.validateTool(toolId);
    EventBundle result = this.tool.start();
  }

  public void stop(Identifier toolId, IdleReason reason) throws NoSuchElementException {
    this.validateTool(toolId);
    EventBundle result = this.tool.stop(reason);
  }

  public void loadPart(Identifier toolId, Part part) throws NoSuchElementException, LoadportFullException {
    this.validateTool(toolId);
    EventBundle result = this.tool.loadPart(part);
  }

  public void unloadPart(Identifier toolId, Identifier port) throws NoSuchElementException, NoPartAvailableException {
    this.validateTool(toolId);
    EventBundle bundle = this.tool.unloadPart();

  }

  public void breakDown(Identifier tool) {

  }

  public void repair(Identifier tool) {

  }

  protected void validateTool(Identifier toolId) throws NoSuchElementException {
    if( ! toolId.equals(this.tool.getId()))
      throw new NoSuchElementException(String.format("no tool with identifier %s found", toolId);
  }
}
